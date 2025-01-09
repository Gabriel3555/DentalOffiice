package gdbv.clinica.servlets;

import java.io.*;

import gdbv.clinica.models.Controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "loginSV", value = "/loginSV")
public class loginSV extends HttpServlet {

    Controller ctrl = new Controller();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String role=request.getParameter("role");

        Object[] credentials = ctrl.autenticate(username, password, role);

        if (credentials != null) {

            HttpSession session = request.getSession();

            session.setAttribute("name", credentials[1]);
            session.setAttribute("last_name", credentials[2]);
            session.setAttribute("email", credentials[3]);
            session.setAttribute("phone_number", credentials[4]);
            session.setAttribute("address", credentials[5]);
            session.setAttribute("birth_date", credentials[6]);
            session.setAttribute("user_id", credentials[8]);

            if(role.equals("Doctor")) {
                session.setAttribute("doctor_id", credentials[0]);
                session.setAttribute("schedule_id", credentials[7]);

                request.getRequestDispatcher("doctor.jsp").forward(request, response);
            } else if (role.equals("Secretary")) {
                session.setAttribute("secretary_id", credentials[0]);
                session.setAttribute("field", credentials[7]);

                request.getRequestDispatcher("secretary.jsp").forward(request, response);
            }

        } else {
            System.out.println("Authentication failed: credentials are null.");
            response.getWriter().write("Invalid username, password, or role.");
        }


    }

    public void destroy() {
    }
}