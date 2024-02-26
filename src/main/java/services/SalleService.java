package services;

import models.Salle_de_sport;
import utils.YasmineDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalleService implements IService<Salle_de_sport>{

    private Connection connection;
    public SalleService(){
        connection = YasmineDatabase.getInstance().getConnection();
    }
    @Override


    public void ajouter_salle(Salle_de_sport salleDeSport) throws SQLException {
        // Vérifier si le nom de la salle est vide
        if (salleDeSport.getNom_salle() == null || salleDeSport.getNom_salle().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la salle ne peut pas être vide.");
        }

        // Vérifier si le nom de la salle est déjà utilisé
        if (isExistingSalle(salleDeSport.getNom_salle())) {
            throw new IllegalArgumentException("Le nom de la salle est déjà utilisé.");
        }

        // Vérifier si l'adresse de la salle est vide
        if (salleDeSport.getAdresse_salle() == null || salleDeSport.getAdresse_salle().isEmpty()) {
            throw new IllegalArgumentException("L'adresse de la salle ne peut pas être vide.");
        }

        // Ajouter la salle
        String req = "INSERT INTO `salle_de_sport` (nom_salle, description_salle, region_salle, image_salle, adresse_salle)" +
                " VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, salleDeSport.getNom_salle());
        preparedStatement.setString(2, salleDeSport.getDescription_salle());
        preparedStatement.setString(3, salleDeSport.getRegion_salle().toString());
        preparedStatement.setString(4, salleDeSport.getImage_salle());
        preparedStatement.setString(5, salleDeSport.getAdresse_salle());
        preparedStatement.executeUpdate();
    }

    // Méthode pour vérifier l'existence d'une salle de sport dans la base de données
    private boolean isExistingSalle(String nomSalle) throws SQLException {
        String req = "SELECT COUNT(*) AS count FROM `salle_de_sport` WHERE `nom_salle`=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, nomSalle);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt("count");
            return count > 0;
        }
        return false;
    }



    @Override
    public void modifier_salle(Salle_de_sport salleDeSport) throws SQLException {
        String sql =" UPDATE `salle_de_sport` SET `nom_salle`=?,`description_salle`=?,`region_salle`=?,`image_salle`=?,`adresse_salle`=? WHERE id_salle=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,salleDeSport.getNom_salle());
        preparedStatement.setString(2,salleDeSport.getDescription_salle());
        preparedStatement.setString(3,salleDeSport.getRegion_salle().toString());
        preparedStatement.setString(4,salleDeSport.getImage_salle());
        preparedStatement.setString(5,salleDeSport.getAdresse_salle());
        preparedStatement.setInt(6,salleDeSport.getId_salle());

        preparedStatement.executeUpdate();

    }

    @Override
    public void supprimer_salle(int id_salle) throws SQLException {
        String sql =" DELETE FROM `salle_de_sport` WHERE id_salle=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id_salle);
        preparedStatement.executeUpdate();

    }

    @Override
    public List<Salle_de_sport> recuperer_salles() throws SQLException {
        String sql ="SELECT * FROM `salle_de_sport`";
        Statement statement = connection.createStatement();
        ResultSet rs =statement.executeQuery(sql);
        List<Salle_de_sport> list =new ArrayList<>();
        while (rs.next()){
            Salle_de_sport s = new Salle_de_sport();
            s.setId_salle(rs.getInt("id_salle"));
            s.setNom_salle(rs.getString("nom_salle"));
            s.setDescription_salle(rs.getString("description_salle"));

            Salle_de_sport.EnumRegion region_salle = Salle_de_sport.EnumRegion.valueOf(rs.getString("region_salle"));
            s.setRegion_salle(region_salle);

            s.setImage_salle(rs.getString("image_salle"));
            s.setAdresse_salle(rs.getString("adresse_salle"));
            list.add(s);
        }
        return list;
    }


}
