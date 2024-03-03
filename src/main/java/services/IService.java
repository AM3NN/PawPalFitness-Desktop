package services;

import models.CategorieProduit;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    void ajouterPrd (T t) throws SQLException;
    void modifierPrd (T t, String reference) throws SQLException;
    void supprimerPrd (int id) throws SQLException;
    List<T> recupererPrd() throws SQLException;
    List<T> filtrerParCategorie(CategorieProduit categorie) throws SQLException;
}
