package model;

import java.sql.Timestamp;

public class PositionGPS {
    private int idPosition;
    private int idBateau;
    private double latitude;
    private double longitude;
    private Timestamp datePosition;

    public PositionGPS() {}

    public PositionGPS(int idPosition, int idBateau, double latitude,
                       double longitude, Timestamp datePosition) {
        this.idPosition = idPosition;
        this.idBateau = idBateau;
        this.latitude = latitude;
        this.longitude = longitude;
        this.datePosition = datePosition;
    }

    public int getIdPosition() { return idPosition; }
    public void setIdPosition(int idPosition) { this.idPosition = idPosition; }
    public int getIdBateau() { return idBateau; }
    public void setIdBateau(int idBateau) { this.idBateau = idBateau; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public Timestamp getDatePosition() { return datePosition; }
    public void setDatePosition(Timestamp datePosition) { this.datePosition = datePosition; }
}