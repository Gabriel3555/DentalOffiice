package gdbv.clinica.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gdbv.clinica.models.Controller;
import gdbv.clinica.models.Turn;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "SvTurnos", urlPatterns = {"/SvTurnos"})
public class SvTurnos extends HttpServlet {

    private Controller control = new Controller();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = request.getSession();
            Long doctorId = (Long) session.getAttribute("doctor_id");

            if (doctorId != null) {
                List<Turn> turns = control.getTurnsByIdDoctor(doctorId);

                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd")
                        .create();
                String jsonTurns = gson.toJson(turns);

                PrintWriter out = response.getWriter();
                out.print(jsonTurns);
                out.flush();
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("No se encontró ID de doctor en la sesión");
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al obtener los turnos: " + e.getMessage());
        }
    }
}