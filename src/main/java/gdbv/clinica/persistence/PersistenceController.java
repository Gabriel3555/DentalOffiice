package gdbv.clinica.persistence;


import gdbv.clinica.models.User;
import gdbv.clinica.services.LoginService;

public class PersistenceController {

    public Object[] autenticate(String email, String password, String role) {
        LoginService loginService = new LoginService();
        return loginService.autenticate(email, password, role);
    }

}
