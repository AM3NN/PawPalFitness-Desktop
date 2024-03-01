package services;

import models.Abonnement;
import utils.YasmineDatabase;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AbonnementService implements IServiceAbonnement<Abonnement>{

    private Connection connection;
    public AbonnementService(){
        connection = YasmineDatabase.getInstance().getConnection();
    }
    @Override


    public void ajouter_abonnement(Abonnement abonnement) throws SQLException {
        // Vérifier si le prix de l'abonnement est valide
        if (abonnement.getPrix_abonnement() <= 0) {
            throw new IllegalArgumentException("Le prix de l'abonnement doit être supérieur à zéro.");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateDebut = sdf.format(abonnement.getDate_deb_abonnement());
        String dateFin = sdf.format(abonnement.getDate_fin_abonnement());

        String req = "INSERT INTO `abonnement`(`id_salle`, `duree_abonnement`, `prix_abonnement`, `date_deb_abonnement`, `date_fin_abonnement`)" +
                "values('" + abonnement.getId_salle() + "','" + abonnement.getDuree_abonnement() + "' , '" + abonnement.getPrix_abonnement() + "', '" + dateDebut + "','" + dateFin + "' )";
        Statement statement = connection.createStatement();
        statement.executeUpdate(req);
    }

    @Override
    public void modifier_abonnement(Abonnement abonnement) throws SQLException {
        String req = "UPDATE `abonnement` SET `id_salle`=?, `duree_abonnement`=?, `prix_abonnement`=?, `date_deb_abonnement`=?, `date_fin_abonnement`=? WHERE `id_abonnement`=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, abonnement.getId_salle());
        preparedStatement.setString(2, abonnement.getDuree_abonnement());
        preparedStatement.setFloat(3, abonnement.getPrix_abonnement());
        preparedStatement.setDate(4, new java.sql.Date(abonnement.getDate_deb_abonnement().getTime()));
        preparedStatement.setDate(5, new java.sql.Date(abonnement.getDate_fin_abonnement().getTime()));
        preparedStatement.setInt(6, abonnement.getId_abonnement());
        preparedStatement.executeUpdate();

    }


    @Override
    public void supprimer_abonnement(int id_abonnement) throws SQLException {
        String req = "DELETE FROM `abonnement` WHERE `id_abonnement`=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, id_abonnement);
        preparedStatement.executeUpdate();

    }

    @Override
    public List<Abonnement> recuperer_abonnements() throws SQLException {
        List<Abonnement> abonnements = new ArrayList<>();
        String req = "SELECT * FROM `abonnement`";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(req);
        while (resultSet.next()) {
            Abonnement abonnement = new Abonnement();
            abonnement.setId_abonnement(resultSet.getInt("id_abonnement"));
            abonnement.setId_salle(resultSet.getInt("id_salle"));
            abonnement.setDuree_abonnement(resultSet.getString("duree_abonnement"));
            abonnement.setPrix_abonnement(resultSet.getFloat("prix_abonnement"));
            abonnement.setDate_deb_abonnement(resultSet.getDate("date_deb_abonnement"));
            abonnement.setDate_fin_abonnement(resultSet.getDate("date_fin_abonnement"));
            abonnements.add(abonnement);
        }
        return abonnements;
    }
}

