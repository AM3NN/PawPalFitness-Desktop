package services;

import models.CategorieProduit;
import models.Produit;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitService implements IService<Produit>{

    private Connection connection;

    public ProduitService(){
        connection=MyDataBase.getInstance().getConnection();
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
    public void modifierPrd(Produit produit) throws SQLException {
        String sql = "UPDATE `produit` SET `reference`=?,`nom`=?,`description`=?,`prix`=?,`image`=?,`categorie`=? WHERE idP=?";

        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setString(1, produit.getReference());
        preparedStatement.setString(2, produit.getNom());
        preparedStatement.setString(3, produit.getDescription());
        preparedStatement.setDouble(4,produit.getPrix());
        preparedStatement.setString(5, produit.getImage());
        preparedStatement.setString(6, produit.getCategorie().toString());
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


    
}
