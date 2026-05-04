package servlet;

import dao.PositionGPSDAO;
import dao.BateauDAO;
import model.PositionGPS;
import model.Bateau;
import model.Utilisateur;
import java.io.IOException;
import java.util.List;
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

	    // Récupérer idPecheur depuis la session
	    Integer idPecheur = (Integer) session.getAttribute("idPecheur");

	    List<Bateau> bateaux;
	    if (idPecheur != null) {
	        bateaux = new BateauDAO().listerParPecheur(idPecheur);
	    } else {
	        // Admin — afficher tous les bateaux
	        bateaux = new BateauDAO().listerTous();
	    }

	    req.setAttribute("bateaux", bateaux);
	    req.getRequestDispatcher("gps.jsp").forward(req, res);
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