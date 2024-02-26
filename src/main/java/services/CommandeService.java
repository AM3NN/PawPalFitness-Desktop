package services;

import models.Commande;
import models.Produit;

import java.sql.Date;

public class CommandeService implements ICommande{
    @Override
    public void ajouterProduitAuPanier(Commande commande, Produit produit) {
        commande.ajouterProduit(produit);
    }

    @Override
    public void supprimerProduitDuPanier(Commande commande, Produit produit) {
        commande.supprimerProduit(produit);
    }

    @Override
    public double calculerMontantTotal(Commande commande) {
        double total = 0;
        for (Produit produit : commande.getProduits()) {
            total += produit.getPrix();
        }
        return total;
    }

    @Override
    public void validerCommande(Commande commande, String adresse, Date dateLivraison) {
        commande.setAdresseUser(adresse);
        commande.setDateLivraison(dateLivraison);
    }
}
