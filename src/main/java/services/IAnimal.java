package Services;

import java.sql.SQLException;
import java.util.List;
import Models.Animal;

public interface IAnimal <T>{
    void ajouterAnimal(T t) throws SQLException;
    List<T> afficherAnimal() throws SQLException;
    List<T> animalparCategorie() throws SQLException;
    List<T> animalparCategorie2() throws SQLException;
    void modifierAnimal(T t) throws SQLException;
    void supprimerAnimal(String nom) throws SQLException;
}
