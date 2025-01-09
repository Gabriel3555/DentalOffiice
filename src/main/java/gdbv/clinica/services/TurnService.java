package gdbv.clinica.services;

import gdbv.clinica.exceptions.SchedulingException;
import gdbv.clinica.models.Doctor;
import gdbv.clinica.models.Patient;
import gdbv.clinica.models.Turn;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TurnService {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("clinicaPersistenceUnit");

    private final DoctorService doctorService = new DoctorService();
    private final PatientService patientService = new PatientService();

    @Transactional
    public Turn createTurn(Long doctorId, Long patientId, LocalDate date, LocalTime time)
            throws Exception {

        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Doctor doctor = em.find(Doctor.class, doctorId);
            if (doctor == null) {
                throw new Exception("Doctor no encontrado con ID: " + doctorId);
            }

            Patient patient = em.find(Patient.class, patientId);
            if (patient == null) {
                throw new Exception("Paciente no encontrado con ID: " + patientId);
            }

            boolean isFree = doctorService.isDoctorAvailable(doctorId, date, time);
            if (!isFree) {
                throw new SchedulingException("El doctor no está disponible en " + date + " " + time);
            }

            if (!isWithinDoctorSchedule(doctor, time)) {
                throw new SchedulingException("Horario fuera del horario de atención del doctor.");
            }

            Turn turn = new Turn();
            turn.setApp_date(date);
            turn.setApp_time(time);
            turn.setDoctor(doctor);
            turn.setPatient(patient);

            em.persist(turn);
            em.getTransaction().commit();

            return turn;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al crear el turno: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Transactional
    public void updateTurn(Turn turn) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Turn existingTurn = em.find(Turn.class, turn.getId());
            if (existingTurn == null) {
                throw new Exception("No se encontró el turno con ID: " + turn.getId());
            }

            boolean isFree = doctorService.isDoctorAvailable(
                    turn.getDoctor().getId(), turn.getApp_date(), turn.getApp_time());
            if (!isFree &&
                    !(existingTurn.getApp_date().equals(turn.getApp_date()) &&
                            existingTurn.getApp_time().equals(turn.getApp_time()))) {
                throw new SchedulingException("El doctor no está disponible en esa fecha/hora.");
            }

            if (!isWithinDoctorSchedule(turn.getDoctor(), turn.getApp_time())) {
                throw new SchedulingException("Horario fuera del horario de atención del doctor.");
            }

            existingTurn.setDoctor(turn.getDoctor());
            existingTurn.setPatient(turn.getPatient());
            existingTurn.setApp_date(turn.getApp_date());
            existingTurn.setApp_time(turn.getApp_time());

            em.merge(existingTurn);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al actualizar el turno: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Transactional
    public void deleteTurn(Long turnId) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Turn turn = em.find(Turn.class, turnId);
            if (turn == null) {
                throw new Exception("Turno no encontrado con ID: " + turnId);
            }

            em.remove(turn);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al eliminar el turno: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Turn getTurnById(Long turnId) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            Turn turn = em.find(Turn.class, turnId);
            if (turn == null) {
                throw new Exception("No se encontró el turno con ID: " + turnId);
            }
            return turn;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Turn> getTurnsByDoctor(Long doctorId) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            // JPQL query to select only necessary fields, avoiding circular references
            TypedQuery<Turn> query = em.createQuery(
                    "SELECT new Turn(t.id, t.app_date, t.app_time, t.patient.id, t.doctor.id, t.patient.name, t.patient.lastName) " +
                            "FROM Turn t WHERE t.doctor.id = :doctorId ORDER BY t.app_date, t.app_time", Turn.class);
            query.setParameter("doctorId", doctorId);
            return query.getResultList();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception("Error fetching turns: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Turn> getAllTurns() throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<Turn> query = em.createQuery("SELECT t FROM Turn t ORDER BY t.app_date, t.app_time", Turn.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener Lista de Turnos: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private boolean isWithinDoctorSchedule(Doctor doctor, LocalTime time) {
        if (doctor.getSchedule() == null) {
            return false;
        }
        LocalTime start = doctor.getSchedule().getStartTime();
        LocalTime end = doctor.getSchedule().getEndTime();
        return (start != null && end != null) && !time.isBefore(start) && !time.isAfter(end);
    }
}