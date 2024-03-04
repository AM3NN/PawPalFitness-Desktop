package services;

import models.CategorieProduit;
import models.Commande;
import models.Produit;
import utils.MyDabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeService implements ICommande{
    private Connection connection;

    public CommandeService(){
        connection= MyDabase.getInstance().getConnection();
    }
    @Override
    public void ajouterProduitAuPanier(Commande commande, Produit produit) {

        commande.ajouterProduit(produit);
    }

    @Override
    public void supprimerProduitDuPanier(Commande commande, Produit produit) {

        commande.supprimerProduit(produit);
    }

    public List<Produit> recupererCommande() throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String req = "SELECT p.* FROM produit p JOIN commande_produit cp ON p.idP = cp.idProduit WHERE cp.idCommande = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, Commande.getIdCommande());
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Produit produit = new Produit(
                    resultSet.getInt("idP"),
                    resultSet.getString("reference"),
                    resultSet.getString("nom"),
                    resultSet.getString("description"),
                    resultSet.getDouble("prix"),
                    resultSet.getString("image"),
                    CategorieProduit.valueOf(resultSet.getString("categorie"))
            );
            produits.add(produit);
        }

        return produits;
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
