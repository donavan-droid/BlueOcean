package service;

import dao.SignalementDAO;
import model.Signalement;
import java.util.*;

public class SignalementService {

    private SignalementDAO signalementDAO = new SignalementDAO();

    public boolean signalerProbleme(int idPecheur, String type,
                                    String description, String localisation) {
        if (description == null || description.isEmpty() ||
            type == null || type.isEmpty()) {
            return false;
        }
        Signalement s = new Signalement(
            0, idPecheur, type, description, localisation, new Date()
        );
        return signalementDAO.ajouter(s);
    }

    public List<Signalement> getTousSignalements() {
        return signalementDAO.listerTous();
    }

    public List<Signalement> getSignalementsPecheur(int idPecheur) {
        return signalementDAO.listerParPecheur(idPecheur);
    }
}