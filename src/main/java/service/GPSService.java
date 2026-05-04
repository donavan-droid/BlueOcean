package service;

import dao.PositionGPSDAO;
import dao.BateauDAO;
import model.PositionGPS;
import model.Bateau;
import java.util.List;

public class GPSService {

    private PositionGPSDAO positionDAO = new PositionGPSDAO();
    private BateauDAO bateauDAO = new BateauDAO();

    public boolean enregistrerPosition(int idBateau,
                                       double latitude, double longitude) {
        // Validation coordonnées Madagascar
        if (latitude < -26 || latitude > -11 ||
            longitude < 43 || longitude > 51) {
            return false;
        }
        PositionGPS pos = new PositionGPS(0, idBateau, latitude, longitude, null);
        return positionDAO.enregistrer(pos);
    }

    public List<PositionGPS> getHistoriquePosition(int idBateau) {
        return positionDAO.listerParBateau(idBateau);
    }

    public PositionGPS getDernierePosition(int idBateau) {
        return positionDAO.getDernierePosition(idBateau);
    }

    public List<Bateau> getBateauxPecheur(int idPecheur) {
        return bateauDAO.listerParPecheur(idPecheur);
    }

    public List<Bateau> getTousBateaux() {
        return bateauDAO.listerTous();
    }

    public boolean ajouterBateau(int idPecheur, String typeBateau) {
        if (typeBateau == null || typeBateau.isEmpty()) return false;
        Bateau b = new Bateau(0, idPecheur, typeBateau);
        return bateauDAO.ajouter(b);
    }
}