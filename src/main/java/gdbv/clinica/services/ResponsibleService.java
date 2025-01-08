package gdbv.clinica.services;

import gdbv.clinica.models.Responsible;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.transaction.Transactional;

import java.util.List;

public class ResponsibleService {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("clinicaPersistenceUnit");

    @Transactional
    public Responsible registerResponsible(Responsible responsible) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            // Valida datos básicos
            if (responsible.getName() == null || responsible.getLastName() == null) {
                throw new Exception("Nombre y apellido son obligatorios para el responsable.");
            }

            // Por si deseas validar la relación (madre, padre, tutor, etc.)
            if (responsible.getRelation() == null) {
                throw new Exception("El campo 'relation' es obligatorio (ej.: Padre, Madre, Tutor).");
            }

            em.persist(responsible);
            em.getTransaction().commit();
            return responsible;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al registrar el responsable: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Transactional
    public void updateResponsible(Responsible responsible) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            // Validar datos mínimos
            if (responsible.getName() == null || responsible.getLastName() == null) {
                throw new Exception("Nombre y apellido son obligatorios.");
            }
            if (responsible.getRelation() == null) {
                throw new Exception("La relación con el paciente es obligatoria.");
            }

            Responsible existingResponsible = em.find(Responsible.class, responsible.getId());
            if (existingResponsible == null) {
                throw new Exception("No se encontró el responsable con ID: " + responsible.getId());
            }

            em.merge(responsible);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al actualizar el responsable: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Transactional
    public void deleteResponsible(Long responsibleId) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Responsible responsible = em.find(Responsible.class, responsibleId);
            if (responsible == null) {
                throw new Exception("No se encontró el responsable con ID: " + responsibleId);
            }

            em.remove(responsible);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al eliminar el responsable: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Responsible getResponsibleById(Long responsibleId) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            Responsible responsible = em.find(Responsible.class, responsibleId);
            if (responsible == null) {
                throw new Exception("No se encontró el responsable con ID: " + responsibleId);
            }
            return responsible;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Responsible> getAllResponsibles() throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String jpql = "SELECT r FROM Responsible r ORDER BY r.lastName, r.name";
            return em.createQuery(jpql, Responsible.class).getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener la lista de responsables: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}