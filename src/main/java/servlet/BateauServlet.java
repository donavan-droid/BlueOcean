package servlet;

import dao.BateauDAO;
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

public class BateauServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.jsp"); return;
        }
        Utilisateur u = (Utilisateur) session.getAttribute("user");
        List<Bateau> liste =
            new BateauDAO().listerParPecheur(u.getIdUser());
        req.setAttribute("bateaux", liste);
        req.getRequestDispatcher("bateau.jsp").forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.jsp"); return;
        }
        Utilisateur u  = (Utilisateur) session.getAttribute("user");
        String action  = req.getParameter("action");

        if ("supprimer".equals(action)) {
            int idBateau = Integer.parseInt(req.getParameter("idBateau"));
            new BateauDAO().supprimer(idBateau);
        } else {
            String type = req.getParameter("typeBateau");
            Bateau b = new Bateau(0, u.getIdUser(), type);
            new BateauDAO().ajouter(b);
        }
        res.sendRedirect("bateau?succes=1");
    }
}