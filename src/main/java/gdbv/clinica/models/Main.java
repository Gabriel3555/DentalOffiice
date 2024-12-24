package gdbv.clinica.models;

import jakarta.persistence.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("clinicaPersistenceUnit");
    }
}
