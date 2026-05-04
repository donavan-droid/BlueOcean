package service;

import dao.UtilisateurDAO;
import dao.PecheurDAO;
import model.Utilisateur;
import model.Pecheur;
import java.util.List;

public class UtilisateurService {

    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    private PecheurDAO pecheurDAO = new PecheurDAO();

    public Utilisateur connecter(String username, String password) {
        if (username == null || username.isEmpty() ||
            password == null || password.isEmpty()) {
            return null;
        }
        return utilisateurDAO.login(username, password);
    }

    public boolean inscrire(String username, String password,
                            String nom, String cin, String telephone) {
        if (username == null || username.isEmpty() ||
            password == null || password.length() < 6) {
            return false;
        }
        Utilisateur u = new Utilisateur(0, username, password, "pecheur");
        boolean ok = utilisateurDAO.inscrire(u);
        if (ok) {
            Pecheur p = new Pecheur(0, nom, cin, telephone);
            pecheurDAO.ajouter(p);
        }
        return ok;
    }

    public List<Utilisateur> listerTousUtilisateurs() {
        return utilisateurDAO.listerTous();
    }

    public boolean supprimerUtilisateur(int idUser) {
        return utilisateurDAO.supprimerUtilisateur(idUser);
    }

    public boolean estAdmin(Utilisateur u) {
        return u != null && "admin".equals(u.getRole());
    }

    public boolean estPecheur(Utilisateur u) {
        return u != null && "pecheur".equals(u.getRole());
    }
}