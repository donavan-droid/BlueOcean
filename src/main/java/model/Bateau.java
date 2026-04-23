package model;

public class Bateau {
    private int idBateau;
    private int idPecheur;
    private String typeBateau;

    public Bateau() {}

    public Bateau(int idBateau, int idPecheur, String typeBateau) {
        this.idBateau = idBateau;
        this.idPecheur = idPecheur;
        this.typeBateau = typeBateau;
    }

    public int getIdBateau() { return idBateau; }
    public void setIdBateau(int idBateau) { this.idBateau = idBateau; }
    public int getIdPecheur() { return idPecheur; }
    public void setIdPecheur(int idPecheur) { this.idPecheur = idPecheur; }
    public String getTypeBateau() { return typeBateau; }
    public void setTypeBateau(String typeBateau) { this.typeBateau = typeBateau; }
}