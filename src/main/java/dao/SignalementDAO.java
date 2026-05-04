package dao;

import model.Signalement;
import java.sql.*;
import java.util.*;

public class SignalementDAO {

    public boolean ajouter(Signalement s) {
        String sql = "INSERT INTO Signalement(idPecheur, type, description, " +
                     "localisation) VALUES(?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, s.getIdPecheur());
            ps.setString(2, s.getType());
            ps.setString(3, s.getDescription());
            ps.setString(4, s.getLocalisation());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Signalement> listerTous() {
        List<Signalement> liste = new ArrayList<>();
        String sql = "SELECT * FROM Signalement ORDER BY dateSignalement DESC";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(new Signalement(
                    rs.getInt("idSignalement"),
                    rs.getInt("idPecheur"),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getString("localisation"),
                    rs.getTimestamp("dateSignalement")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public List<Signalement> listerParPecheur(int idPecheur) {
        List<Signalement> liste = new ArrayList<>();
        String sql = "SELECT * FROM Signalement WHERE idPecheur=? " +
                     "ORDER BY dateSignalement DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPecheur);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                liste.add(new Signalement(
                    rs.getInt("idSignalement"),
                    rs.getInt("idPecheur"),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getString("localisation"),
                    rs.getTimestamp("dateSignalement")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
}