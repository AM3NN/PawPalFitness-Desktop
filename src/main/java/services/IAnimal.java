package services;

import java.sql.SQLException;
import java.util.List;
import models.Animal;

public interface IAnimal <T>{
    void ajouterAnimal(T t) throws SQLException;
    List<T> afficherAnimal() throws SQLException;
    List<T> animalparCategorie() throws SQLException;
    List<T> animalparCategorie2() throws SQLException;
    void modifierAnimal(T t, String n) throws SQLException;
    void supprimerAnimal(String nom) throws SQLException;
    List<Animal> animalParNom(String n) throws SQLException;

    List<Animal> rechCaractere() throws SQLException;
}
