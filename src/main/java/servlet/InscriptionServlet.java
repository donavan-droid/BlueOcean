package servlet;

import dao.UtilisateurDAO;
import dao.PecheurDAO;
import model.Utilisateur;
import model.Pecheur;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InscriptionServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String nom      = req.getParameter("nom");
        String cin      = req.getParameter("cin");
        String tel      = req.getParameter("telephone");

        Utilisateur u = new Utilisateur(0, username, password, "pecheur");
        UtilisateurDAO uDao = new UtilisateurDAO();
        boolean ok = uDao.inscrire(u);

        if (ok) {
            Pecheur p = new Pecheur(0, nom, cin, tel);
            new PecheurDAO().ajouter(p);
            res.sendRedirect("login.jsp?inscription=ok");
        } else {
            req.setAttribute("erreur", "Erreur lors de l'inscription");
            req.getRequestDispatcher("inscription.jsp").forward(req, res);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.getRequestDispatcher("inscription.jsp").forward(req, res);
    }
}