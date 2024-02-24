package services;

import models.Travailleur;
import utils.MyDabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TravailleurService implements IService<Travailleur> {
    private Connection connection;

    public TravailleurService() {
        connection = MyDabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Travailleur travailleur) throws SQLException {
        String personneSql = "INSERT INTO personne (nom, prenom, age, region, email, password, role_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement personneStatement = connection.prepareStatement(personneSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            personneStatement.setString(1, travailleur.getNom());
            personneStatement.setString(2, travailleur.getPrenom());
            personneStatement.setInt(3, travailleur.getAge());
            personneStatement.setString(4, travailleur.getRegion());
            personneStatement.setString(5, travailleur.getEmail());
            personneStatement.setString(6, travailleur.getPassword());
            personneStatement.setInt(7, travailleur.getRoleId()); // Set the role_id here

            int affectedRows = personneStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating personne failed, no rows affected.");
            }

            try (var generatedKeys = personneStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    travailleur.setPersonneId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating personne failed, no ID obtained.");
                }
            }
        }

        String travailleurSql = "INSERT INTO travailleur (personne_id, diplome, categorie, langue, experience) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement travailleurStatement = connection.prepareStatement(travailleurSql)) {
            travailleurStatement.setInt(1, travailleur.getPersonneId());
            travailleurStatement.setString(2, travailleur.getDiplome());
            travailleurStatement.setString(3, travailleur.getCategorie());
            travailleurStatement.setString(4, travailleur.getLangue());
            travailleurStatement.setString(5, travailleur.getExperience());

            travailleurStatement.executeUpdate();
        }
    }

    @Override
    public void modifier(Travailleur travailleur) throws SQLException {
        // Implement modifier logic
    }

    @Override
    public void supprimer(int id) throws SQLException {
        // Implement supprimer logic
    }

    @Override
    public List<Travailleur> recuperer() throws SQLException {
        List<Travailleur> travailleurs = new ArrayList<>();
        String sql = "SELECT * FROM personne JOIN travailleur ON personne.id = travailleur.personne_id WHERE personne.role_id = 3";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int personneId = resultSet.getInt("personne_id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                int age = resultSet.getInt("age");
                String region = resultSet.getString("region");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                int roleId = resultSet.getInt("role_id");
                String diplome = resultSet.getString("diplome");
                String categorie = resultSet.getString("categorie");
                String langue = resultSet.getString("langue");
                String experience = resultSet.getString("experience");
                Travailleur travailleur = new Travailleur(age, nom, prenom, region, email, password, roleId, diplome, categorie, langue, experience);
                travailleurs.add(travailleur);
            }
        }
        return travailleurs;
    }

}
