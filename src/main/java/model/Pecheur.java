package model;

public class Pecheur {
    private int idPecheur;
    private String nom;
    private String cin;
    private String telephone;

    public Pecheur() {}

    public Pecheur(int idPecheur, String nom, String cin, String telephone) {
        this.idPecheur = idPecheur;
        this.nom = nom;
        this.cin = cin;
        this.telephone = telephone;
    }

    public int getIdPecheur() { return idPecheur; }
    public void setIdPecheur(int idPecheur) { this.idPecheur = idPecheur; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
}