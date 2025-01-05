package gdbv.clinica.models;

import gdbv.clinica.services.LoginService;
import jakarta.persistence.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("clinicaPersistenceUnit");
    }
}
