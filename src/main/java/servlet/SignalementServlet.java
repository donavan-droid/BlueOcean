package servlet;

import dao.SignalementDAO;
import model.Signalement;
import model.Utilisateur;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignalementServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.jsp"); return;
        }
        Utilisateur u = (Utilisateur) session.getAttribute("user");
        List<Signalement> liste =
            new SignalementDAO().listerParPecheur(u.getIdUser());
        req.setAttribute("signalements", liste);
        req.getRequestDispatcher("signalement.jsp").forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.jsp"); return;
        }
        Utilisateur u = (Utilisateur) session.getAttribute("user");
        String type        = req.getParameter("type");
        String description = req.getParameter("description");
        String localisation= req.getParameter("localisation");

        Signalement s = new Signalement(
            0, u.getIdUser(), type, description, localisation, new java.util.Date()
        );
        new SignalementDAO().ajouter(s);
        res.sendRedirect("signalement?succes=1");
    }
}