package model;

import java.util.Date;

public class Capture {
    private int idCapture;
    private int idPecheur;
    private String typePoisson;
    private double poids;
    private Date dateCapture;

    public Capture() {}

    public Capture(int idCapture, int idPecheur, String typePoisson,
                   double poids, Date dateCapture) {
        this.idCapture = idCapture;
        this.idPecheur = idPecheur;
        this.typePoisson = typePoisson;
        this.poids = poids;
        this.dateCapture = dateCapture;
    }

    public int getIdCapture() { return idCapture; }
    public void setIdCapture(int idCapture) { this.idCapture = idCapture; }
    public int getIdPecheur() { return idPecheur; }
    public void setIdPecheur(int idPecheur) { this.idPecheur = idPecheur; }
    public String getTypePoisson() { return typePoisson; }
    public void setTypePoisson(String typePoisson) { this.typePoisson = typePoisson; }
    public double getPoids() { return poids; }
    public void setPoids(double poids) { this.poids = poids; }
    public Date getDateCapture() { return dateCapture; }
    public void setDateCapture(Date dateCapture) { this.dateCapture = dateCapture; }
}