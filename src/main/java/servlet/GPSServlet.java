package servlet;

import dao.PositionGPSDAO;
import dao.BateauDAO;
import model.PositionGPS;
import model.Bateau;
import model.Utilisateur;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GPSServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException {
	    HttpSession session = req.getSession(false);
	    if (session == null || session.getAttribute("user") == null) {
	        res.sendRedirect("login.jsp"); return;
	    }

	    String role = (String) session.getAttribute("role");
	    BateauDAO bateauDAO = new BateauDAO();
	    PositionGPSDAO posDAO = new PositionGPSDAO();

	    if ("admin".equals(role)) {
	        // Admin : récupérer tous les bateaux + leur dernière position
	        List<Bateau> tousLesBateaux = bateauDAO.listerTous();
	        List<Map<String, Object>> bateauxAvecPosition = new ArrayList<>();

	        for (Bateau b : tousLesBateaux) {
	            PositionGPS pos = posDAO.getDernierePosition(b.getIdBateau());
	            Map<String, Object> data = new HashMap<>();
	            data.put("bateau", b);
	            data.put("position", pos);
	            bateauxAvecPosition.add(data);
	        }

	        req.setAttribute("bateauxAvecPosition", bateauxAvecPosition);
	        req.setAttribute("role", role);
	        req.getRequestDispatcher("gps_admin.jsp").forward(req, res);
	    } else {
	        // Pêcheur : afficher ses bateaux
	        Integer idPecheur = (Integer) session.getAttribute("idPecheur");
	        List<Bateau> bateaux = idPecheur != null
	            ? bateauDAO.listerParPecheur(idPecheur)
	            : new ArrayList<>();
	        req.setAttribute("bateaux", bateaux);
	        req.getRequestDispatcher("gps.jsp").forward(req, res);
	    }
	}

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        int idBateau    = Integer.parseInt(req.getParameter("idBateau"));
        double latitude  = Double.parseDouble(req.getParameter("latitude"));
        double longitude = Double.parseDouble(req.getParameter("longitude"));

        PositionGPS pos = new PositionGPS(0, idBateau, latitude, longitude, null);
        new PositionGPSDAO().enregistrer(pos);

        res.setContentType("application/json");
        res.getWriter().write("{\"status\":\"ok\"}");
    }
}