package servlet;

import dao.UtilisateurDAO;
import model.Utilisateur;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UtilisateurDAO dao = new UtilisateurDAO();
        Utilisateur u = dao.login(username, password);

        if (u != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", u);
            session.setAttribute("role", u.getRole());

            if ("admin".equals(u.getRole())) {
                res.sendRedirect("dashboard_admin.jsp");
            } else {
                res.sendRedirect("dashboard_pecheur.jsp");
            }
        } else {
            req.setAttribute("erreur", "Identifiants incorrects");
            req.getRequestDispatcher("login.jsp").forward(req, res);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, res);
    }
}