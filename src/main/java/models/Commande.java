package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Commande {
    private int idC;
    private int idUtilisateur;
    private String adresseUser;
    private Date dateCommande;
    private Date dateLivraison;
    private double prixTotal;
    private List<Produit> produits;

    public Commande() {
        produits = new ArrayList<>();
    }

    public Commande(int idC, int idUtilisateur, String adresseUser, Date dateCommande, Date dateLivraison, double prixTotal, List<Produit> produits) {
        this.idC = idC;
        this.idUtilisateur = idUtilisateur;
        this.adresseUser = adresseUser;
        this.dateCommande = dateCommande;
        this.dateLivraison = dateLivraison;
        this.prixTotal = prixTotal;
        this.produits = produits;
    }

    public Commande(int idUtilisateur, String adresseUser, Date dateCommande, Date dateLivraison, double prixTotal, List<Produit> produits) {
        this.idUtilisateur = idUtilisateur;
        this.adresseUser = adresseUser;
        this.dateCommande = dateCommande;
        this.dateLivraison = dateLivraison;
        this.prixTotal = prixTotal;
        this.produits = produits;
    }

    public int getIdCommande() {
        return idC;
    }

    public void setIdCommande(int idCommande) {
        this.idC = idCommande;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getAdresseUser() {
        return adresseUser;
    }

    public void setAdresseUser(String adresseUser) {
        this.adresseUser = adresseUser;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Date getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }


    public void ajouterProduit(Produit produit) {
        produits.add(produit);
    }

    public void supprimerProduit(Produit produit) {
        produits.remove(produit);
    }
}
