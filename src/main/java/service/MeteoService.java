package service;

import dao.AlerteDAO;
import model.Alerte;
import java.io.*;
import java.net.*;
import java.sql.Date;
import java.util.List;

public class MeteoService {

    private static final String API_KEY = "5517c7ef765ef534d38d5fc4da208a03";
    private AlerteDAO alerteDAO = new AlerteDAO();

    public String getMeteoJSON(double latitude, double longitude) {
        String urlStr = "https://api.openweathermap.org/data/2.5/weather?"
                + "lat=" + latitude + "&lon=" + longitude
                + "&appid=" + API_KEY + "&units=metric&lang=fr";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
            return sb.toString();
        } catch (Exception e) {
            return "{\"error\":\"API météo indisponible\"}";
        }
    }

    public boolean creerAlerte(String type, String niveau, String message) {
        if (type == null || niveau == null || message == null) return false;
        Alerte a = new Alerte(
            0,
            new Date(System.currentTimeMillis()),
            type, niveau, message, null
        );
        return alerteDAO.ajouter(a);
    }

    public List<Alerte> getAlertes() {
        return alerteDAO.listerToutes();
    }

    public void verifierEtCreerAlerte(String jsonMeteo) {
        if (jsonMeteo.contains("\"storm\"") ||
            jsonMeteo.contains("\"thunderstorm\"")) {
            creerAlerte("Tempête", "ÉLEVÉ",
                "Conditions météo dangereuses détectées !");
        } else if (jsonMeteo.contains("\"rain\"")) {
            creerAlerte("Pluie", "MODÉRÉ",
                "Pluie prévue, sortie en mer déconseillée.");
        }
    }
}