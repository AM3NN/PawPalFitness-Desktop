package Services;

import Models.Animal;
import Models.Favori;
import Utils.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriService implements IFavori<Favori> {

    private Connection connection;
    Animal a=new Animal();
    Favori f = new Favori();
    public FavoriService(){
        connection = DB.getInstance().getConnection();
    }
    @Override
    public void ajouterFAnimal(Favori favori,String n) throws SQLException {
        String req="insert into favoris(`noma`, `IDA`) SELECT Nom,IDA FROM animal WHERE Nom =?";

        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1,n);
        preparedStatement.execute();

    }

    @Override
    public List<Favori> afficherFAnimal() throws SQLException {
        String req = "SELECT noma FROM favoris";
        Statement statement = connection.createStatement();

        ResultSet resultat = statement.executeQuery(req);
        List<Favori> list = new ArrayList<>();
        while (resultat.next()){
            Favori f = new Favori();
            f.setNoma(resultat.getString("noma"));
            list.add(f);
        }
        return list;
    }

    @Override
    public void supprimerFavori(String nom) throws SQLException {
        String req="DELETE FROM `favoris` where noma = ? ";

        PreparedStatement prestatement = connection.prepareStatement(req);
        prestatement.setString(1, nom);
        prestatement.executeUpdate();
    }
}
