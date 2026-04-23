package model;

import java.sql.Timestamp;

public class Alerte {
    private int idAlerte;
    private String message;
    private Timestamp dateAlerte;

    public Alerte() {}

    public Alerte(int idAlerte, String message, Timestamp dateAlerte) {
        this.idAlerte = idAlerte;
        this.message = message;
        this.dateAlerte = dateAlerte;
    }

    public int getIdAlerte() { return idAlerte; }
    public void setIdAlerte(int idAlerte) { this.idAlerte = idAlerte; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Timestamp getDateAlerte() { return dateAlerte; }
    public void setDateAlerte(Timestamp dateAlerte) { this.dateAlerte = dateAlerte; }
}