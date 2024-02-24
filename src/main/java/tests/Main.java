package tests;

import models.Personne;
import models.Travailleur;
import services.PersonneService;
import services.TravailleurService;
import utils.MyDabase;

import java.sql.SQLException;

public class Main {

    public static void main(String [] args){


        //MyDabase d=MyDabase.getInstance();

        TravailleurService t=new TravailleurService();

        try {
            t.ajouter(new Travailleur(18, "laouini", "amen", "manouba", "amenallah.laouini@esprit.tn", "aaaa", 2, "String diplome", "aa", "arabe", "0"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(t.recuperer());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
