package gdbv.clinica.servlets;

import java.io.*;

import gdbv.clinica.models.Controller;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "loginSV", value = "/loginSV")
public class loginSV extends HttpServlet {

    Controller ctrl = new Controller();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String role=request.getParameter("role");

        Object[] credentials = ctrl.authenticate(username, password, role);

        for (Object o : credentials) {
            System.out.println(o);
        }

        if (credentials != null) {
/*
            HttpSession session = request.getSession();
            session.setAttribute("name", credentials[0]);
            session.setAttribute("last_name", credentials[1]);
            session.setAttribute("email", credentials[2]);
*/
        }


    }

    public void destroy() {
    }
}