package gdbv.clinica.models;

import gdbv.clinica.persistence.PersistenceController;

public class Controller {

    public Object[] authenticate(String username, String password, String role) {
        PersistenceController pc = new PersistenceController();
        return pc.autenticate(username, password, role);
    }
}
