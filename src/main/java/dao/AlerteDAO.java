package dao;

import model.Alerte;
import java.sql.*;
import java.util.*;

public class AlerteDAO {

    public boolean ajouter(Alerte a) {
        String sql = "INSERT INTO Alerte(date, type, niveau, message) VALUES(?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, a.getDate());
            ps.setString(2, a.getType());
            ps.setString(3, a.getNiveau());
            ps.setString(4, a.getMessage());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Alerte> listerToutes() {
        List<Alerte> liste = new ArrayList<>();
        String sql = "SELECT * FROM Alerte ORDER BY dateAlerte DESC";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(new Alerte(
                    rs.getInt("idAlerte"),
                    rs.getDate("date"),
                    rs.getString("type"),
                    rs.getString("niveau"),
                    rs.getString("message"),
                    rs.getTimestamp("dateAlerte")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
}