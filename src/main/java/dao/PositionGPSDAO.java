package dao;

import model.PositionGPS;
import java.sql.*;
import java.util.*;

public class PositionGPSDAO {

    public boolean enregistrer(PositionGPS p) {
        String sql = "INSERT INTO PositionGPS(idBateau, latitude, longitude) VALUES(?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getIdBateau());
            ps.setDouble(2, p.getLatitude());
            ps.setDouble(3, p.getLongitude());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<PositionGPS> listerParBateau(int idBateau) {
        List<PositionGPS> liste = new ArrayList<>();
        String sql = "SELECT * FROM PositionGPS WHERE idBateau=? ORDER BY datePosition DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idBateau);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                liste.add(new PositionGPS(
                    rs.getInt("idPosition"),
                    rs.getInt("idBateau"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude"),
                    rs.getTimestamp("datePosition")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public PositionGPS getDernierePosition(int idBateau) {
        String sql = "SELECT * FROM PositionGPS WHERE idBateau=? ORDER BY datePosition DESC LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idBateau);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new PositionGPS(
                    rs.getInt("idPosition"),
                    rs.getInt("idBateau"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude"),
                    rs.getTimestamp("datePosition")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}