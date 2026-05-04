package dao;

import model.Bateau;
import java.sql.*;
import java.util.*;

public class BateauDAO {

    public boolean ajouter(Bateau b) {
        String sql = "INSERT INTO Bateau(idPecheur, typeBateau) VALUES(?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, b.getIdPecheur());
            ps.setString(2, b.getTypeBateau());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Bateau> listerParPecheur(int idPecheur) {
        List<Bateau> liste = new ArrayList<>();
        String sql = "SELECT * FROM Bateau WHERE idPecheur=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPecheur);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                liste.add(new Bateau(
                    rs.getInt("idBateau"),
                    rs.getInt("idPecheur"),
                    rs.getString("typeBateau")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public List<Bateau> listerTous() {
        List<Bateau> liste = new ArrayList<>();
        String sql = "SELECT * FROM Bateau";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(new Bateau(
                    rs.getInt("idBateau"),
                    rs.getInt("idPecheur"),
                    rs.getString("typeBateau")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public boolean supprimer(int id) {
        String sql = "DELETE FROM Bateau WHERE idBateau=?";
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