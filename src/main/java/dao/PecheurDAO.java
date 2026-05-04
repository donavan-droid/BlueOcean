package dao;

import model.Pecheur;
import java.sql.*;
import java.util.*;

public class PecheurDAO {

    public boolean ajouter(Pecheur p) {
        String sql = "INSERT INTO Pecheur(nom, CIN, telephone) VALUES(?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNom());
            ps.setString(2, p.getCin());
            ps.setString(3, p.getTelephone());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Pecheur trouverParId(int id) {
        String sql = "SELECT * FROM Pecheur WHERE idPecheur=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Pecheur(
                    rs.getInt("idPecheur"),
                    rs.getString("nom"),
                    rs.getString("CIN"),
                    rs.getString("telephone")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Pecheur trouverParUsername(String username) {
        // On joint Utilisateur et Pecheur via le nom
        // Pour l'instant on cherche par nom = username
        String sql = "SELECT p.* FROM Pecheur p " +
                     "JOIN Utilisateur u ON u.username = ? " +
                     "WHERE p.idPecheur = u.idUser";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Pecheur(
                    rs.getInt("idPecheur"),
                    rs.getString("nom"),
                    rs.getString("CIN"),
                    rs.getString("telephone")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Pecheur> listerTous() {
        List<Pecheur> liste = new ArrayList<>();
        String sql = "SELECT * FROM Pecheur";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(new Pecheur(
                    rs.getInt("idPecheur"),
                    rs.getString("nom"),
                    rs.getString("CIN"),
                    rs.getString("telephone")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public boolean modifier(Pecheur p) {
        String sql = "UPDATE Pecheur SET nom=?, CIN=?, telephone=? WHERE idPecheur=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNom());
            ps.setString(2, p.getCin());
            ps.setString(3, p.getTelephone());
            ps.setInt(4, p.getIdPecheur());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean supprimer(int id) {
        String sql = "DELETE FROM Pecheur WHERE idPecheur=?";
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