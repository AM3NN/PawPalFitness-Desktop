package services;

import java.sql.SQLException;
import java.util.List;

public interface ICategorie <T>{
    void ajouterCategorie(T t) throws SQLException;
    List<T> afficherCategorie() throws SQLException;

    void supprimerCategorie(String nom) throws SQLException;
}
