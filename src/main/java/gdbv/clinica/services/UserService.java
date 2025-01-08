package gdbv.clinica.services;

import gdbv.clinica.models.User;
import gdbv.clinica.models.Doctor;
import gdbv.clinica.models.Secretary;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

public class UserService {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("clinicaPersistenceUnit");

    @Transactional
    public User registerUser(User user) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return user;
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error registrando el usuario: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Transactional
    public void updateUser(User user) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            User existingUser = em.find(User.class, user.getId());
            if (existingUser == null) {
                throw new Exception("No se encontró el usuario con ID: " + user.getId());
            }

            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al actualizar el usuario: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Transactional
    public void deleteUser(Long userId) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            User user = em.find(User.class, userId);
            if (user == null) {
                throw new Exception("Usuario no encontrado (ID: " + userId + ").");
            }

            em.remove(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al eliminar el usuario: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public User getUserById(Long userId) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            User user = em.find(User.class, userId);
            if (user == null) {
                throw new Exception("No se encontró el usuario con ID: " + userId);
            }
            return user;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> getAllUsers() throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener todos los usuarios: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public User getUserByUserName(String userName) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.userName = :userName",
                    User.class
            );
            query.setParameter("userName", userName);
            List<User> results = query.getResultList();
            if (results.isEmpty()) {
                return null;
            }
            return results.get(0);
        } catch (Exception e) {
            throw new Exception("Error al obtener usuario por userName: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Transactional
    public void linkDoctorToUser(Long userId, Long doctorId) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            User user = em.find(User.class, userId);
            if (user == null) {
                throw new Exception("Usuario no encontrado con ID: " + userId);
            }

            Doctor doctor = em.find(Doctor.class, doctorId);
            if (doctor == null) {
                throw new Exception("Doctor no encontrado con ID: " + doctorId);
            }

            if (user.getSecretary() != null) {
                throw new Exception("El usuario ya está asociado a un secretary. No se puede asignar un doctor simultáneamente.");
            }

            user.setDoctor(doctor);
            doctor.setUser(user);

            em.merge(user);
            em.merge(doctor);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al asociar doctor al usuario: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Transactional
    public void linkSecretaryToUser(Long userId, Long secretaryId) throws Exception {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            User user = em.find(User.class, userId);
            if (user == null) {
                throw new Exception("Usuario no encontrado con ID: " + userId);
            }

            Secretary secretary = em.find(Secretary.class, secretaryId);
            if (secretary == null) {
                throw new Exception("Secretaria/o no encontrado con ID: " + secretaryId);
            }

            if (user.getDoctor() != null) {
                throw new Exception("El usuario ya está asociado a un doctor. No se puede asignar una secretary simultáneamente.");
            }

            user.setSecretary(secretary);
            secretary.setUser(user);

            em.merge(user);
            em.merge(secretary);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al asociar secretaria/o al usuario: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}