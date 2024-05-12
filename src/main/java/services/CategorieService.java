package services;

import models.Categorie;
import utils.MyDabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieService implements ICategorie<Categorie>{
    private Connection connection;

    public CategorieService(){
        connection = MyDabase.getInstance().getConnection();
    }
    @Override
    public void ajouterCategorie(Categorie categorie) throws SQLException {
        String req = "INSERT INTO categorie (nomc) VALUES (?)";

        PreparedStatement statement = connection.prepareStatement(req);
        statement.setString(1, categorie.getNomc());

        statement.executeUpdate();
    }

    @Override
    public List<Categorie> afficherCategorie() throws SQLException {
        String req = "SELECT nomc FROM categorie";
        Statement statement = connection.createStatement();

        ResultSet resultat = statement.executeQuery(req);
        List<Categorie> list = new ArrayList<>();
        while (resultat.next()){
            Categorie c = new Categorie();
            c.setNomc(resultat.getString("nomc"));
            list.add(c);
        }
        return list;
    }

    @Override
    public void supprimerCategorie(String nom) throws SQLException {
        String req="DELETE FROM `categorie` where nomc = ? ";

        PreparedStatement prestatement = connection.prepareStatement(req);
        prestatement.setString(1, nom);
        prestatement.executeUpdate();
    }
}
