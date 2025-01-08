package gdbv.clinica.services;

import gdbv.clinica.exceptions.DoctorException;
import gdbv.clinica.models.Secretary;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.transaction.Transactional;
import java.util.List;

public class SecretaryService {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("clinicaPersistenceUnit");

    @Transactional
    public Secretary registerSecretary(Secretary secretary) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            if (secretary.getName() == null || secretary.getLastName() == null || secretary.getEmail() == null) {
                throw new Exception("Nombre, apellido y correo electrónico son obligatorios para la secretaria.");
            }

            em.persist(secretary);
            em.getTransaction().commit();
            return secretary;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al registrar la secretaria: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Transactional
    public void updateSecretary(Secretary secretary) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            if (secretary.getName() == null || secretary.getLastName() == null || secretary.getEmail() == null) {
                throw new Exception("Nombre, apellido y correo electrónico son obligatorios.");
            }

            Secretary existingSecretary = em.find(Secretary.class, secretary.getId());
            if (existingSecretary == null) {
                throw new Exception("No se encontró la secretaria con ID: " + secretary.getId());
            }

            em.merge(secretary);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al actualizar la secretaria: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Transactional
    public void deleteSecretary(Long secretaryId) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Secretary secretary = em.find(Secretary.class, secretaryId);
            if (secretary == null) {
                throw new Exception("No se encontró la secretaria con ID: " + secretaryId);
            }

            em.remove(secretary);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al eliminar la secretaria: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Secretary getSecretaryById(Long secretaryId) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            Secretary secretary = em.find(Secretary.class, secretaryId);
            if (secretary == null) {
                throw new Exception("No se encontró la secretaria con ID: " + secretaryId);
            }
            return secretary;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Secretary> getAllSecretaries() throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String jpql = "SELECT s FROM Secretary s ORDER BY s.lastName, s.name";
            return em.createQuery(jpql, Secretary.class).getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener la lista de secretarias: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}