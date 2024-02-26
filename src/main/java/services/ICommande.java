package services;

import models.Commande;
import models.Produit;

import java.sql.Date;


public interface ICommande <T>{
    void ajouterProduitAuPanier(Commande commande, Produit produit);
    void supprimerProduitDuPanier(Commande commande, Produit produit);
    double calculerMontantTotal(Commande commande);
    void validerCommande(Commande commande, String adresse, Date dateLivraison);
}
