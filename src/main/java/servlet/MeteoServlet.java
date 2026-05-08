package servlet;

import dao.AlerteDAO;
import model.Alerte;
import java.io.*;
import java.net.*;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MeteoServlet extends HttpServlet {


    private static final String API_KEY = "5517c7ef765ef534d38d5fc4da208a03";
    private static final String VILLE   = "Antananarivo,MG";

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.jsp"); return;
        }

        String role = (String) session.getAttribute("role");

        // Appel API météo
        String urlStr = "https://api.openweathermap.org/data/2.5/weather?q="
                + VILLE + "&appid=" + API_KEY + "&units=metric&lang=fr";
        String jsonMeteo = "";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
            jsonMeteo = sb.toString();
        } catch (Exception e) {
            jsonMeteo = "{\"error\":\"API indisponible\"}";
        }

        List<Alerte> alertes = new AlerteDAO().listerToutes();
        req.setAttribute("jsonMeteo", jsonMeteo);
        req.setAttribute("alertes", alertes);
        req.setAttribute("role", role);  // ← passer le rôle à la JSP
        req.getRequestDispatcher("meteo.jsp").forward(req, res);
    }
}