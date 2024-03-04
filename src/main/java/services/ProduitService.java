package services;

import javafx.scene.image.Image;
import models.CategorieProduit;
import models.Produit;
import utils.MyDabase;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitService implements IServiceP<Produit>{

    private Connection connection;

    public ProduitService(){
        connection=MyDabase.getInstance().getConnection();
    }

    @Override
    public void ajouterPrd(Produit produit) throws SQLException {
        if (!produit.estValide()) {
            throw new IllegalArgumentException("Le produit n'est pas valide. Veuillez remplir tous les champs convenablement.");
        }

        String req="insert into `produit`(`reference`, `nom`, `description`, `prix`, `image`, `categorie`)"+
                "values('"+ produit.getReference() + "','"+ produit.getNom()+ "','"+ produit.getDescription()+"',"
                +produit.getPrix() + ",'"+ produit.getImage()+ "','"+ produit.getCategorie()+ "')";
        Statement statement=connection.createStatement();
        statement.executeUpdate(req);

    }

    @Override
    public void modifierPrd(Produit produit, String reference) throws SQLException {
        String sql = "UPDATE `produit` SET `nom`=?,`description`=?,`prix`=?,`image`=?,`categorie`=?,`reference`=? WHERE idP=?";

        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setString(1, produit.getNom());
        preparedStatement.setString(2, produit.getDescription());
        preparedStatement.setDouble(3,produit.getPrix());
        preparedStatement.setString(4, produit.getImage());
        preparedStatement.setString(5, produit.getCategorie().toString());
        preparedStatement.setString(6, produit.getReference());
        preparedStatement.setInt(7, produit.getIdP());

        preparedStatement.executeUpdate();

    }

    @Override
    public void supprimerPrd(int id) throws SQLException {
        // Vérifier si le produit existe
        String selectSql = "SELECT idP FROM produit WHERE idP=?";

        PreparedStatement selectStatement = connection.prepareStatement(selectSql);
        selectStatement.setInt(1, id);

        ResultSet resultSet = selectStatement.executeQuery();

        if (!resultSet.next()) {
            System.out.println("Le produit avec l'ID " + id + " n'existe pas.");
            return;
        }

        String deleteSql = "DELETE FROM produit WHERE idP=?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
        preparedStatement.setInt(1, id);

        preparedStatement.executeUpdate();

    }

    @Override
    public List<Produit> recupererPrd() throws SQLException {

        List<Produit> produits = new ArrayList<>();
        String req = "SELECT * FROM produit";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
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



    public List<Produit> filtrerParCategorie(CategorieProduit categorie) throws SQLException {
        List<Produit> produitsFiltres = new ArrayList<>();
        String sql = "SELECT * FROM produit WHERE categorie = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, categorie.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Produit produit = new Produit();
                    produit.setIdP(resultSet.getInt("idP"));
                    produit.setNom(resultSet.getString("nom"));
                    produit.setDescription(resultSet.getString("description"));
                    produit.setPrix(resultSet.getDouble("prix"));
                    produit.setImage(resultSet.getString("image"));
                    produit.setCategorie(CategorieProduit.valueOf(resultSet.getString("categorie")));
                    produitsFiltres.add(produit);
                }
            }
        }
        return produitsFiltres;
    }



}
