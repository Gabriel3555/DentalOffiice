package gdbv.clinica.services;

import gdbv.clinica.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class LoginService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("clinicaPersistenceUnit");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public User autenticate(String username, String password) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user = em.createQuery("SELECT u FROM User u WHERE u.user_name = :username AND u.password = :password", User.class)
                            .setParameter("username", username)
                            .setParameter("password", password)
                            .getSingleResult();
            em.getTransaction().commit();

            return user;
        } catch (Exception e) {
            System.err.println("Error al autenticar usuario: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
