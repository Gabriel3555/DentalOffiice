package gdbv.clinica.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@WebServlet(name = "SvTurnos", urlPatterns = {"/SvTurnos"})
public class SvTurnos extends HttpServlet {

    private Controller control = new Controller();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession();
            Long doctorId = (Long) session.getAttribute("doctor_id");

            if (doctorId != null) {
                List<Turn> turns = control.getTurnsByIdDoctor(doctorId);

                // Convertir a JSON usando Gson
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .create();

                String jsonTurns = gson.toJson(turns);
                out.print(jsonTurns);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print("{\"error\": \"Session expired or invalid doctor ID\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String action = request.getParameter("action");
            if ("delete".equals(action)) {
                String idStr = request.getParameter("id");
                System.out.println("ID recibido para eliminar: " + idStr);

                if (idStr != null && !idStr.trim().isEmpty()) {
                    Long turnId = Long.parseLong(idStr);
                    control.deleteTurn(turnId);
                    out.print("{\"success\": true, \"message\": \"Turno eliminado correctamente\"}");
                } else {
                    out.print("{\"success\": false, \"message\": \"ID de turno no válido\"}");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        }
    }

    public static class LocalTimeAdapter extends TypeAdapter<LocalTime> {
        @Override
        public void write(JsonWriter out, LocalTime value) throws IOException {
            out.value(value == null ? null : value.toString());
        }

        @Override
        public LocalTime read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            return LocalTime.parse(in.nextString());
        }
    }

    public static class LocalDateAdapter extends TypeAdapter<LocalDate> {
        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            out.value(value == null ? null : value.toString());
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            return LocalDate.parse(in.nextString());
        }
    }
}