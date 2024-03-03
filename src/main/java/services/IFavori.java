package Services;

import java.sql.SQLException;
import java.util.List;

public interface IFavori <T>{
    void ajouterFAnimal(T t,String n) throws SQLException;
    List<T> afficherFAnimal() throws SQLException;

    void supprimerFavori(String nom) throws SQLException;
}
