package services;

import models.Commande;
import models.Produit;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;


public interface ICommande <T>{
    void ajouterProduitAuPanier(Commande commande, Produit produit);
    void supprimerProduitDuPanier(Commande commande, Produit produit);
    public List<Produit> recupererCommande() throws SQLException;
    double calculerMontantTotal(Commande commande);
    void validerCommande(Commande commande, String adresse, Date dateLivraison);

}
