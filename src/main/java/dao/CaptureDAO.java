package dao;

import model.Capture;
import java.sql.*;
import java.util.*;

public class CaptureDAO {

    public boolean ajouter(Capture c) {
        String sql = "INSERT INTO Capture(idPecheur, typePoisson, poids, dateCapture) VALUES(?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getIdPecheur());
            ps.setString(2, c.getTypePoisson());
            ps.setDouble(3, c.getPoids());
            ps.setDate(4, new java.sql.Date(c.getDateCapture().getTime()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Capture> listerParPecheur(int idPecheur) {
        List<Capture> liste = new ArrayList<>();
        String sql = "SELECT * FROM Capture WHERE idPecheur=? ORDER BY dateCapture DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPecheur);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                liste.add(new Capture(
                    rs.getInt("idCapture"),
                    rs.getInt("idPecheur"),
                    rs.getString("typePoisson"),
                    rs.getDouble("poids"),
                    rs.getDate("dateCapture")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public List<Capture> listerToutes() {
        List<Capture> liste = new ArrayList<>();
        String sql = "SELECT * FROM Capture ORDER BY dateCapture DESC";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(new Capture(
                    rs.getInt("idCapture"),
                    rs.getInt("idPecheur"),
                    rs.getString("typePoisson"),
                    rs.getDouble("poids"),
                    rs.getDate("dateCapture")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public boolean supprimer(int id) {
        String sql = "DELETE FROM Capture WHERE idCapture=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}