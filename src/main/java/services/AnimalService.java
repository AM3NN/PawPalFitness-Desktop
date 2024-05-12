package services;
import models.Animal;
import utils.MyDabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
public class AnimalService implements IAnimal<Animal>{

    private Connection connection;

    public AnimalService(){
        connection = MyDabase.getInstance().getConnection();
    }
    @Override
    public void ajouterAnimal(Animal animal) throws SQLException {
        String req = "INSERT INTO animal (IDC, IDU, Nom, Age, Type, Details, Poids) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(req);
        statement.setInt(1, animal.getIDC());
        statement.setInt(2, animal.getIDU());
        statement.setString(3, animal.getNom());
        statement.setInt(4, animal.getAge());
        statement.setString(5, animal.getType());
        statement.setString(6, animal.getDetails());
        statement.setFloat(7, animal.getPoids());

        statement.executeUpdate();
    }

    public int fetchCategoryId(String categoryName) {
        int categoryId = -1;

        try {
            // Prepare a statement to query the database
            String query = "SELECT IDC FROM categorie WHERE nomc = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, categoryName);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if the result set has any rows
            if (resultSet.next()) {
                // Retrieve the category ID from the result set
                categoryId = resultSet.getInt("IDC");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in your application
        }

        return categoryId;
    }

    @Override
    public List<Animal> afficherAnimal() throws SQLException {
        String req = "SELECT Nom,Age,Type,Details,Poids FROM animal";
        Statement statement = connection.createStatement();

        ResultSet resultat = statement.executeQuery(req);
        List<Animal> list = new ArrayList<>();
        while (resultat.next()){
            Animal a = new Animal();
            a.setNom(resultat.getString("Nom"));
            a.setAge(resultat.getInt("Age"));
            a.setType(resultat.getString("Type"));
            a.setDetails(resultat.getString("Details"));
            a.setPoids(resultat.getFloat("Poids"));
            list.add(a);
        }
        return list;
    }

    @Override
    public void modifierAnimal(Animal animal,String n) throws SQLException {
        String req="update animal set age = ?,details = ?,poids= ? where nom = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, animal.getAge());
        preparedStatement.setString(2,animal.getDetails());
        preparedStatement.setFloat(3,animal.getPoids());
        preparedStatement.setString(4, n);
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
        // Fetch the category ID for the category name "Chat"
        int categoryId = fetchCategoryId("Chat");

        // Check if the category ID is valid
        if (categoryId == -1) {
            // Handle the case where the category does not exist
            System.out.println("Category not found: Chat");
            return new ArrayList<>(); // Return an empty list
        }

        // Prepare the SQL query to select animals with the category ID for "Chat"
        String req = "SELECT Nom,Age,Poids FROM animal WHERE IDC = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, categoryId);

        // Execute the query
        ResultSet resultat = preparedStatement.executeQuery();

        // Process the results and create Animal objects
        List<Animal> list = new ArrayList<>();
        while (resultat.next()) {
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
        // Fetch the category ID for the category name "Chien"
        int categoryId = fetchCategoryId("Chien");

        // Check if the category ID is valid
        if (categoryId == -1) {
            // Handle the case where the category does not exist
            System.out.println("Category not found: Chien");
            return new ArrayList<>(); // Return an empty list
        }

        // Prepare the SQL query to select animals with the category ID for "Chien"
        String req = "SELECT Nom,Age,Details,Poids FROM animal WHERE IDC = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, categoryId);

        // Execute the query
        ResultSet resultat = preparedStatement.executeQuery();

        // Process the results and create Animal objects
        List<Animal> list = new ArrayList<>();
        while (resultat.next()) {
            Animal a = new Animal();
            a.setNom(resultat.getString("Nom"));
            a.setAge(resultat.getInt("Age"));
            a.setDetails(resultat.getString("Details"));
            a.setPoids(resultat.getFloat("Poids"));
            list.add(a);
        }

        return list;
    }


    @Override
        public List<Animal> animalParNom(String n) throws SQLException {
            String req = "SELECT Age,Details,Poids FROM animal WHERE nom=?";
            PreparedStatement statement = connection.prepareStatement(req);
            statement.setString(1,n);

            ResultSet resultat = statement.executeQuery();
            List<Animal> list = new ArrayList<>();
            while (resultat.next()){
                Animal a = new Animal();
                a.setAge(resultat.getInt("Age"));
                a.setDetails(resultat.getString("Details"));
                a.setPoids(resultat.getFloat("Poids"));
                list.add(a);
            }
            return list;
        }

   @Override
    public List<Animal> rechCaractere() throws SQLException {
        String req = "SELECT Nom,Age,Details,Poids FROM animal WHERE nom LIKE ?";
        PreparedStatement statement = connection.prepareStatement(req);
        statement.setString(1,"_%");

        ResultSet resultat = statement.executeQuery();
        List<models.Animal> list = new ArrayList<>();
        while (resultat.next()){
            models.Animal a = new models.Animal();
            a.setNom(resultat.getString("Nom"));
            a.setAge(resultat.getInt("Age"));
            a.setDetails(resultat.getString("Details"));
            a.setPoids(resultat.getFloat("Poids"));
            list.add(a);
        }
        return list;
    }
}
