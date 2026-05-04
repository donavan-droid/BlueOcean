package model;

public class Administrateur {
    private int idUser;
    private String username;
    private String password;
    private String niveau;

    public Administrateur() {}

    public Administrateur(int idUser, String username, String password, String niveau) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.niveau = niveau;
    }

    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
}