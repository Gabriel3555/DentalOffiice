package gdbv.clinica.services;

import gdbv.clinica.models.Schedule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.transaction.Transactional;

import java.util.List;

public class ScheduleService {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("clinicaPersistenceUnit");

    @Transactional
    public Schedule createSchedule(Schedule schedule) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            if (schedule.getStartTime() == null || schedule.getEndTime() == null) {
                throw new Exception("El horario debe tener una hora de inicio y una hora de fin.");
            }

            if (!schedule.getEndTime().isAfter(schedule.getStartTime())) {
                throw new Exception("La hora de fin debe ser posterior a la hora de inicio.");
            }

            em.persist(schedule);
            em.getTransaction().commit();

            return schedule;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al crear el horario: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Transactional
    public void updateSchedule(Schedule schedule) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Schedule existingSchedule = em.find(Schedule.class, schedule.getId());
            if (existingSchedule == null) {
                throw new Exception("No se encontró el horario con ID: " + schedule.getId());
            }

            if (schedule.getStartTime() == null || schedule.getEndTime() == null) {
                throw new Exception("El horario debe tener una hora de inicio y una hora de fin.");
            }
            if (!schedule.getEndTime().isAfter(schedule.getStartTime())) {
                throw new Exception("La hora de fin debe ser posterior a la hora de inicio.");
            }

            em.merge(schedule);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al actualizar el horario: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Transactional
    public void deleteSchedule(Long scheduleId) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Schedule schedule = em.find(Schedule.class, scheduleId);
            if (schedule == null) {
                throw new Exception("No se encontró el horario con ID: " + scheduleId);
            }

            em.remove(schedule);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al eliminar el horario: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public Schedule getScheduleById(Long scheduleId) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            Schedule schedule = em.find(Schedule.class, scheduleId);
            if (schedule == null) {
                throw new Exception("No se encontró el horario con ID: " + scheduleId);
            }
            return schedule;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Schedule> getAllSchedules() throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            return em.createQuery("SELECT s FROM Schedule s ORDER BY s.startTime", Schedule.class)
                    .getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener la lista de horarios: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}