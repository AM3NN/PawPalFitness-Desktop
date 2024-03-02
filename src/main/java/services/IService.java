package services;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    void ajouterPrd (T t) throws SQLException;
    void modifierPrd (T t, String reference) throws SQLException;
    void supprimerPrd (int id) throws SQLException;
    List<T> recupererPrd() throws SQLException;
}
