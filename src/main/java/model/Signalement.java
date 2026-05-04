package model;

import java.util.Date;

public class Signalement {
    private int idSignalement;
    private int idPecheur;
    private String type;
    private String description;
    private String localisation;
    private Date dateSignalement;

    public Signalement() {}

    public Signalement(int idSignalement, int idPecheur, String type,
                       String description, String localisation, Date dateSignalement) {
        this.idSignalement = idSignalement;
        this.idPecheur = idPecheur;
        this.type = type;
        this.description = description;
        this.localisation = localisation;
        this.dateSignalement = dateSignalement;
    }

    public int getIdSignalement() { return idSignalement; }
    public void setIdSignalement(int idSignalement) { this.idSignalement = idSignalement; }
    public int getIdPecheur() { return idPecheur; }
    public void setIdPecheur(int idPecheur) { this.idPecheur = idPecheur; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocalisation() { return localisation; }
    public void setLocalisation(String localisation) { this.localisation = localisation; }
    public Date getDateSignalement() { return dateSignalement; }
    public void setDateSignalement(Date dateSignalement) { this.dateSignalement = dateSignalement; }
}