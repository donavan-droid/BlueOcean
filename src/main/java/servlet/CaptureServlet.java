package servlet;

import dao.CaptureDAO;
import dao.PecheurDAO;
import model.Capture;
import model.Utilisateur;
import model.Pecheur;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CaptureServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.jsp"); return;
        }

        Utilisateur u = (Utilisateur) session.getAttribute("user");

        // Trouver le pecheur lié à cet utilisateur par username
        PecheurDAO pecheurDAO = new PecheurDAO();
        Pecheur pecheur = pecheurDAO.trouverParUsername(u.getUsername());

        if (pecheur == null) {
            req.setAttribute("erreur", "Profil pecheur introuvable.");
            req.setAttribute("captures", new java.util.ArrayList<>());
            req.getRequestDispatcher("captures.jsp").forward(req, res);
            return;
        }

        session.setAttribute("idPecheur", pecheur.getIdPecheur());

        List<Capture> liste = new CaptureDAO()
            .listerParPecheur(pecheur.getIdPecheur());
        req.setAttribute("captures", liste);
        req.getRequestDispatcher("captures.jsp").forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.jsp"); return;
        }

        Integer idPecheur = (Integer) session.getAttribute("idPecheur");
        if (idPecheur == null) {
            res.sendRedirect("captures"); return;
        }

        String typePoisson = req.getParameter("typePoisson");
        double poids = Double.parseDouble(req.getParameter("poids"));

        Capture c = new Capture(0, idPecheur, typePoisson, poids, new Date());
        new CaptureDAO().ajouter(c);
        res.sendRedirect("captures");
    }
}