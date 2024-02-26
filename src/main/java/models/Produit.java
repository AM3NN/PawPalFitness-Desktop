package models;

import java.util.Objects;

public class Produit {
    private int idP;
    private String reference;
    private String nom;
    private String description;
    private double prix;
    private String image;
    private CategorieProduit categorie;

    public Produit(int idP, String reference, String nom, String description, double prix, String image, CategorieProduit categorie) {
        this.idP = idP;
        this.reference = reference;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.image = image;
        this.categorie = categorie;
    }

    public Produit(String reference, String nom, String description, double prix, String image, CategorieProduit categorie) {
        this.reference = reference;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.image = image;
        this.categorie = categorie;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public CategorieProduit getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieProduit categorie) {
        this.categorie = categorie;
    }

    public boolean estValide() {
        return reference != null && !reference.isEmpty() &&
                nom != null && !nom.isEmpty() &&
                description != null && !description.isEmpty() &&
                prix > 0 &&
                image != null && !image.isEmpty() &&
                categorie != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produit produit = (Produit) o;
        return idP == produit.idP && Double.compare(prix, produit.prix) == 0 && Objects.equals(reference, produit.reference) && Objects.equals(nom, produit.nom) && Objects.equals(description, produit.description) && Objects.equals(image, produit.image) && categorie == produit.categorie;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idP, reference, nom, description, prix, image, categorie);
    }

    @Override
    public String toString() {
        return "Produit{" +
                "idP=" + idP +
                ", reference='" + reference + '\'' +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", image='" + image + '\'' +
                ", categorie=" + categorie +
                '}';
    }

}
