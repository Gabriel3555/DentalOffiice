package gdbv.clinica.models;

import gdbv.clinica.services.LoginService;
import jakarta.persistence.*;

public class Main {
    public static void main(String[] args) {
      /*LoginService loginService = new LoginService();
        System.out.println(loginService.autenticate("juan", "monda"));*/

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("clinicaPersistenceUnit");
    }
}
