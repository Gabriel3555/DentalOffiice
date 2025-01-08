package gdbv.clinica.services;

import gdbv.clinica.models.Doctor;
import gdbv.clinica.models.Schedule;
import gdbv.clinica.models.Turn;
import gdbv.clinica.exceptions.DoctorException;
import gdbv.clinica.exceptions.SchedulingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class DoctorService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("clinicaPersistenceUnit");

    @Transactional
    public Doctor registerDoctor(Doctor doctor, Schedule schedule) throws DoctorException {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            if (doctor.getName() == null || doctor.getLastName() == null || doctor.getEmail() == null) {
                throw new DoctorException("Nombre, apellido y correo electrónico son obligatorios.");
            }

            em.persist(schedule);
            doctor.setSchedule(schedule);
            em.persist(doctor);
            em.getTransaction().commit();
            return doctor;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DoctorException("Error al registrar el doctor: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    @Transactional
    public void updateDoctor(Doctor doctor) throws DoctorException {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            if (doctor.getName() == null || doctor.getLastName() == null || doctor.getEmail() == null) {
                throw new DoctorException("Nombre, apellido y correo electrónico son obligatorios.");
            }

            em.merge(doctor);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DoctorException("Error al actualizar el doctor: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Transactional
    public void deleteDoctor(Long doctorId) throws DoctorException {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Doctor doctor = em.find(Doctor.class, doctorId);
            if (doctor == null) {
                throw new DoctorException("Doctor no encontrado.");
            }

            em.remove(doctor);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DoctorException("Error al eliminar el doctor: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Schedule getDoctorSchedule(Long doctorId) throws DoctorException {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            Doctor doctor = em.find(Doctor.class, doctorId);
            if (doctor == null) {
                throw new DoctorException("Doctor no encontrado.");
            }
            return doctor.getSchedule();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean isDoctorAvailable(Long doctorId, LocalDate date, LocalTime time) throws DoctorException {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<Long> query = em.createQuery(
                            "SELECT COUNT(t) FROM Turn t WHERE t.doctor.id = :doctorId AND t.app_date = :date AND t.app_time = :time",
                            Long.class)
                    .setParameter("doctorId", doctorId)
                    .setParameter("date", date)
                    .setParameter("time", time);
            Long count = query.getSingleResult();
            return count == 0;
        } catch (Exception e) {
            throw new DoctorException("Error al verificar la disponibilidad del doctor: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public List<Turn> getDoctorTurns(Long doctorId, LocalDate date) throws DoctorException {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<Turn> query = em.createQuery(
                            "SELECT t FROM Turn t WHERE t.doctor.id = :doctorId AND t.app_date = :date",
                            Turn.class)
                    .setParameter("doctorId", doctorId)
                    .setParameter("date", date);
            return query.getResultList();
        } catch (Exception e) {
            throw new DoctorException("Error al obtener los turnos del doctor: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}