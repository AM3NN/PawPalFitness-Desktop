package tests;


import models.CategorieProduit;
import models.Produit;
import services.ProduitService;
import utils.MyDataBase;

import java.sql.SQLException;

public class Main {
    public static void main (String [] args){
        //MyDataBase db=new MyDataBase();

        String categ = "PreWorkout";
        CategorieProduit categorieProduit = CategorieProduit.valueOf(categ);

        ProduitService ps=new ProduitService();
        /*
        try {
            ps.ajouterPrd(new Produit("01","BSN","blabla",22.5,"C:/Users/EYA/Desktop/Esprit/PI/gestion des produits/categories/PreWorkout/BSN.jpg",categorieProduit));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            ps.ajouterPrd(new Produit("PRD03","tt","blaa",52.5,"C:/Users/EYA/Desktop/Esprit/PI/gestion des produits/categories/PreWorkout/BSN.jpg",categorieProduit));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        try {
            ps.modifierPrd(new Produit(2,"PRD01","test","boubou",38.9,"C:/Users/EYA/Desktop/Esprit/PI/gestion des produits/categories/PreWorkout/BSN.jpg",categorieProduit));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/

        try {
            ps.supprimerPrd(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
