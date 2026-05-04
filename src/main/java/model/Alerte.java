package model;

import java.sql.Timestamp;

public class Alerte {
    private int idAlerte;
    private java.sql.Date date;
    private String type;
    private String niveau;
    private String message;
    private Timestamp dateAlerte;

    public Alerte() {}

    public Alerte(int idAlerte, java.sql.Date date, String type,
                       String niveau, String message, Timestamp dateAlerte) {
        this.idAlerte = idAlerte;
        this.date = date;
        this.type = type;
        this.niveau = niveau;
        this.message = message;
        this.dateAlerte = dateAlerte;
    }

    public int getIdAlerte() { return idAlerte; }
    public void setIdAlerte(int idAlerte) { this.idAlerte = idAlerte; }
    public java.sql.Date getDate() { return date; }
    public void setDate(java.sql.Date date) { this.date = date; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Timestamp getDateAlerte() { return dateAlerte; }
    public void setDateAlerte(Timestamp dateAlerte) { this.dateAlerte = dateAlerte; }
}