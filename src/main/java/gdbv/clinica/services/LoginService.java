package gdbv.clinica.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class LoginService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("clinicaPersistenceUnit");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Object[] autenticate(String username, String password, String role) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Long userId = em.createQuery(
                            "SELECT u.id FROM User u WHERE u.userName = :username AND u.password = :password AND u.role = :role",
                            Long.class
                    )
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .setParameter("role", role)
                    .getSingleResult();

            if (userId == null) {
                System.out.println("User not found.");
                return null;
            }

            if (role.equals("Doctor")) {
                Object[] doctorData = em.createQuery(
                                "SELECT d.id, d.name, d.lastName, d.email, d.phoneNumber, d.address, d.bornDate, d.schedule.id, d.user.id " +
                                        "FROM Doctor d WHERE d.user.id = :userId",
                                Object[].class
                        )
                        .setParameter("userId", userId)
                        .getSingleResult();

                if (doctorData == null) {
                    System.out.println("Doctor not found for user ID: " + userId);
                    return null;
                }

                return doctorData;
            } else if (role.equals("Secretaria")) {
                Object[] secretaryData = em.createQuery(
                                "SELECT s.id, s.name, s.lastName, s.email, s.phoneNumber, s.address, s.bornDate, s.field, s.user.id " +
                                        "FROM Secretary s WHERE s.user.id = :userId",
                                Object[].class
                        )
                        .setParameter("userId", userId)
                        .getSingleResult();

                if (secretaryData == null) {
                    System.out.println("Secretary not found for user ID: " + userId);
                    return null;
                }

                return secretaryData;
            }

            return null;

        } catch (Exception e) {
            System.err.println("Error during authentication: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
