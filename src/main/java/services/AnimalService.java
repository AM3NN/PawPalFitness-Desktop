package Services;
import Models.Animal;
import Utils.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
public class AnimalService implements IAnimal<Animal>{

    private Connection connection;

    public AnimalService(){
        connection = DB.getInstance().getConnection();
    }
    @Override
    public void ajouterAnimal(Animal animal) throws SQLException {
        String req="insert into animal(`Nom`, `Age`, `Categorie`, `Type`, `Details`,`Poids`)"
                +"values('"+animal.getNom()+"','"+animal.getAge()+"','"
                +animal.getCategorie()+"','"+animal.getType()+"','"
                +animal.getDetails()+"','"+animal.getPoids()+"')";

        Statement statement = connection.createStatement();
        statement.executeUpdate(req);
    }

    @Override
    public List<Animal> afficherAnimal() throws SQLException {
        String req = "SELECT Nom,Age,Categorie,Type,Details,Poids FROM animal";
        Statement statement = connection.createStatement();

        ResultSet resultat = statement.executeQuery(req);
        List<Animal> list = new ArrayList<>();
        while (resultat.next()){
            Animal a = new Animal();
            a.setNom(resultat.getString("Nom"));
            a.setAge(resultat.getInt("Age"));
            a.setCategorie(resultat.getString("Categorie"));
            a.setCategorie(resultat.getString("Type"));
            a.setCategorie(resultat.getString("Details"));
            a.setPoids(resultat.getFloat("Poids"));
            list.add(a);
        }
        return list;
    }

    @Override
    public void modifierAnimal(Animal animal) throws SQLException {
        String req="update animal set nom = ?, age = ?, type= ?,details = ?,poids= ? where nom = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, animal.getNom());
        preparedStatement.setInt(2, animal.getAge());
        preparedStatement.setString(3,animal.getType());
        preparedStatement.setString(4,animal.getDetails());
        preparedStatement.setFloat(5,animal.getPoids());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimerAnimal(String nom) throws SQLException {
       String req="DELETE FROM `animal` where nom = ? ";

        PreparedStatement prestatement = connection.prepareStatement(req);
        prestatement.setString(1, nom);
        prestatement.executeUpdate();
    }

    @Override
    public List<Animal> animalparCategorie() throws SQLException {
        String req = "SELECT Nom,Age,Poids FROM animal WHERE Categorie='Chat'";
        Statement statement = connection.createStatement();

        ResultSet resultat = statement.executeQuery(req);
        List<Animal> list = new ArrayList<>();
        while (resultat.next()){
            Animal a = new Animal();
            a.setNom(resultat.getString("Nom"));
            a.setAge(resultat.getInt("Age"));
            a.setPoids(resultat.getFloat("Poids"));
            list.add(a);
        }
        return list;
    }

    @Override
    public List<Animal> animalparCategorie2() throws SQLException {
        String req = "SELECT Nom,Age,Poids FROM animal WHERE Categorie='Chien'";
        Statement statement = connection.createStatement();

        ResultSet resultat = statement.executeQuery(req);
        List<Animal> list = new ArrayList<>();
        while (resultat.next()){
            Animal a = new Animal();
            a.setNom(resultat.getString("Nom"));
            a.setAge(resultat.getInt("Age"));
            a.setPoids(resultat.getFloat("Poids"));
            list.add(a);
        }
        return list;
    }
}
