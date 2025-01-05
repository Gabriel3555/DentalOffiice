package gdbv.clinica.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

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

            // Get the user ID
            List<Long> userIds = em.createQuery(
                            "SELECT u.id FROM User u WHERE u.userName = :username AND u.password = :password AND u.role = :role",
                            Long.class
                    )
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .setParameter("role", role)
                    .getResultList();

            if (userIds.isEmpty()) {
                System.out.println("User not found.");
                return null;
            }

            Long userId = userIds.get(0);

            // Get role-specific information
            if (role.equals("Doctor")) {
                List<Object[]> doctorData = em.createQuery(
                                "SELECT d.id, d.name, d.lastName, d.email, d.phoneNumber, d.address, d.user.id " +
                                        "FROM Doctor d WHERE d.user.id = :userId",
                                Object[].class
                        )
                        .setParameter("userId", userId)
                        .getResultList();

                if (doctorData.isEmpty()) {
                    System.out.println("Doctor not found for user ID: " + userId);
                    return null;
                }

                return doctorData.get(0);
            } else if (role.equals("Secretaria")) {
                List<Object[]> secretaryData = em.createQuery(
                                "SELECT s.id, s.name, s.lastName, s.email, s.phoneNumber, s.address, s.bornDate, s.field, s.user.id " +
                                        "FROM Secretary s WHERE s.user.id = :userId",
                                Object[].class
                        )
                        .setParameter("userId", userId)
                        .getResultList();

                if (secretaryData.isEmpty()) {
                    System.out.println("Secretary not found for user ID: " + userId);
                    return null;
                }

                return secretaryData.get(0);
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
