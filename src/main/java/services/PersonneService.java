package services;

import models.Personne;
import utils.MyDabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonneService implements IService<Personne> {

    private Connection connection;

    public PersonneService() {
        connection = MyDabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Personne personne) throws SQLException {
        String sql = "INSERT INTO personne (nom, prenom, age, region, email, password, role_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, personne.getNom());
        preparedStatement.setString(2, personne.getPrenom());
        preparedStatement.setInt(3, personne.getAge());
        preparedStatement.setString(4, personne.getRegion());
        preparedStatement.setString(5, personne.getEmail());
        preparedStatement.setString(6, personne.getPassword());
        preparedStatement.setInt(7, personne.getRoleId());

        preparedStatement.executeUpdate();
    }

    @Override
    public void modifier(Personne personne) throws SQLException {
        String sql = "UPDATE personne SET nom = ?, prenom = ?, age = ?, region = ?, email = ?, password = ?, role_id = ? WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, personne.getNom());
        preparedStatement.setString(2, personne.getPrenom());
        preparedStatement.setInt(3, personne.getAge());
        preparedStatement.setString(4, personne.getRegion());
        preparedStatement.setString(5, personne.getEmail());
        preparedStatement.setString(6, personne.getPassword());
        preparedStatement.setInt(7, personne.getRoleId());
        preparedStatement.setInt(8, personne.getId());

        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM personne WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Personne> recuperer() throws SQLException {
        String sql = "SELECT * FROM personne WHERE role_id = 2";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        List<Personne> list = new ArrayList<>();
        while (rs.next()) {
            Personne p = new Personne(
                    rs.getInt("id"),
                    rs.getInt("age"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("region"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getInt("role_id")
            );
            list.add(p);
        }
        return list;
    }
}
