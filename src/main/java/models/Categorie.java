package models;

public class Categorie {

    int IDC;
    String nomc;
    public Categorie() {

    }
    public Categorie(int IDC, String nomc) {
        this.IDC = IDC;
        this.nomc = nomc;
    }

    public Categorie(String nomc) {
        this.nomc = nomc;
    }

    public int getIDC() {
        return IDC;
    }

    public String getNomc() {
        return nomc;
    }

    public void setNomc(String nomc) {
        this.nomc = nomc;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "IDC=" + IDC +
                ", nomc='" + nomc + '\'' +
                '}';
    }
}
