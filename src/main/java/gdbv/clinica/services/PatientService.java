package gdbv.clinica.services;

import gdbv.clinica.models.Patient;
import gdbv.clinica.models.Responsible;
import gdbv.clinica.models.Turn;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("clinicaPersistenceUnit");

    @Transactional
    public Patient registerPatient(Patient patient, Responsible responsible) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            if (patient.getName() == null || patient.getLastName() == null) {
                throw new Exception("Nombre y apellido son obligatorios.");
            }

            if (patient.getBornDate() == null) {
                throw new Exception("La fecha de nacimiento es obligatoria.");
            }

            int age = calculateAge(patient.getBornDate());

            if (age < 18 && responsible == null) {
                throw new Exception("Los pacientes menores de edad deben tener un responsable asignado.");
            }

            if (responsible != null) {
                em.persist(responsible);
                patient.setResponsible(responsible);
            }

            em.persist(patient);
            em.getTransaction().commit();
            return patient;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al registrar el paciente: " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Transactional
    public void updatePatient(Patient patient) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            if (patient.getName() == null || patient.getLastName() == null) {
                throw new Exception("Nombre y apellido son obligatorios.");
            }

            if (patient.getBornDate() == null) {
                throw new Exception("La fecha de nacimiento es obligatoria.");
            }

            Patient existingPatient = em.find(Patient.class, patient.getId());
            if (existingPatient == null) {
                throw new Exception("Paciente no encontrado.");
            }

            int age = calculateAge(patient.getBornDate());

            if (age < 18 && patient.getResponsible() == null) {
                throw new Exception("Los pacientes menores de edad deben tener un responsable asignado.");
            }

            em.merge(patient);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al actualizar el paciente: " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Transactional
    public void deletePatient(Long patientId) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Patient patient = em.find(Patient.class, patientId);
            if (patient == null) {
                throw new Exception("Paciente no encontrado.");
            }

            List<Turn> pendingTurns = getPendingTurns(patientId);
            if (!pendingTurns.isEmpty()) {
                throw new Exception("No se puede eliminar el paciente porque tiene turnos pendientes.");
            }

            em.remove(patient);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al eliminar el paciente: " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public int getPatientAge(Long patientId) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            Patient patient = em.find(Patient.class, patientId);
            if (patient == null) {
                throw new Exception("Paciente no encontrado.");
            }
            return calculateAge(patient.getBornDate());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Patient> searchPatientsByAgeRange(int minAge, int maxAge) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            LocalDate maxBirthDate = LocalDate.now().minusYears(minAge);
            LocalDate minBirthDate = LocalDate.now().minusYears(maxAge + 1);

            TypedQuery<Patient> query = em.createQuery(
                    "SELECT p FROM Patient p WHERE p.bornDate BETWEEN :minBirth AND :maxBirth",
                    Patient.class);
            query.setParameter("minBirth", minBirthDate);
            query.setParameter("maxBirth", maxBirthDate);
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al buscar pacientes por rango de edad: " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Turn> getPatientTurns(Long patientId) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<Turn> query = em.createQuery(
                    "SELECT t FROM Turn t WHERE t.patient.id = :patientId ORDER BY t.app_date, t.app_time",
                    Turn.class);
            query.setParameter("patientId", patientId);
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener los turnos del paciente: " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private List<Turn> getPendingTurns(Long patientId) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<Turn> query = em.createQuery(
                    "SELECT t FROM Turn t WHERE t.patient.id = :patientId AND t.app_date >= :currentDate",
                    Turn.class);
            query.setParameter("patientId", patientId);
            query.setParameter("currentDate", LocalDate.now());
            return query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Map<String, Long> getInsuranceStatistics() throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            // Contar pacientes con obra social
            TypedQuery<Long> withInsuranceQuery = em.createQuery(
                    "SELECT COUNT(p) FROM Patient p WHERE p.hasInsurance = true",
                    Long.class
            );
            Long withInsurance = withInsuranceQuery.getSingleResult();

            // Contar pacientes sin obra social
            TypedQuery<Long> withoutInsuranceQuery = em.createQuery(
                    "SELECT COUNT(p) FROM Patient p WHERE p.hasInsurance = false",
                    Long.class
            );
            Long withoutInsurance = withoutInsuranceQuery.getSingleResult();

            Map<String, Long> statistics = new HashMap<>();
            statistics.put("withInsurance", withInsurance);
            statistics.put("withoutInsurance", withoutInsurance);

            return statistics;
        } catch (Exception e) {
            throw new Exception("Error al obtener estadísticas de obra social: " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return 0;
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public List<Patient> getMinorPatients() throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            LocalDate cutoffDate = LocalDate.now().minusYears(18);

            TypedQuery<Patient> query = em.createQuery(
                    "SELECT p FROM Patient p WHERE p.bornDate > :cutoffDate",
                    Patient.class);
            query.setParameter("cutoffDate", cutoffDate);
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener pacientes menores de edad: " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean isMinor(Long patientId) throws Exception {
        return getPatientAge(patientId) < 18;
    }
}