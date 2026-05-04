package service;

import dao.CaptureDAO;
import model.Capture;
import java.util.*;

public class CaptureService {

    private CaptureDAO captureDAO = new CaptureDAO();

    public boolean enregistrerCapture(int idPecheur, String typePoisson,
                                      double poids) {
        if (typePoisson == null || typePoisson.isEmpty() || poids <= 0) {
            return false;
        }
        Capture c = new Capture(0, idPecheur, typePoisson, poids, new Date());
        return captureDAO.ajouter(c);
    }

    public List<Capture> getCapturesPecheur(int idPecheur) {
        return captureDAO.listerParPecheur(idPecheur);
    }

    public List<Capture> getToutesCaptures() {
        return captureDAO.listerToutes();
    }

    public boolean supprimerCapture(int idCapture) {
        return captureDAO.supprimer(idCapture);
    }

    // Calcul total des captures (en kg) pour un pêcheur
    public double getTotalPoids(int idPecheur) {
        List<Capture> liste = captureDAO.listerParPecheur(idPecheur);
        double total = 0;
        for (Capture c : liste) total += c.getPoids();
        return total;
    }

    // Espèce la plus capturée
    public String getEspecePrincipale(int idPecheur) {
        List<Capture> liste = captureDAO.listerParPecheur(idPecheur);
        Map<String, Double> map = new HashMap<>();
        for (Capture c : liste) {
            map.merge(c.getTypePoisson(), c.getPoids(), Double::sum);
        }
        return map.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("Aucune");
    }
}