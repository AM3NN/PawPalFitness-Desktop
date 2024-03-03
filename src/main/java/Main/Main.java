package Main;

import Models.Animal;
import Services.AnimalService;
import Utils.APIConnector;
import Utils.DB;

import java.sql.SQLException;

public class Main {
    public static void main(String [] args){
        //DB d = DB.getInstance();
        //APIConnector ap = new APIConnector("http://api.weatherapi.com/v1/current.json?key=bf46852e640a439382e104225240303&q=");
        //AnimalService as = new AnimalService();
        /*try {
            as.ajouterAnimal(new Animal(7,"Rocky","chien", 25));
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }*/

       /* try {
            as.modifierAnimal(new Animal(1,"rock",7,"chien",23));
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }*/

        /*try {
            System.out.println(as.afficherAnimal());
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }

        try {
            as.supprimerAnimal("rock");
                System.out.println("done");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
    }
}
