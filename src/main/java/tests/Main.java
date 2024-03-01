package tests;

import models.Abonnement;
import models.Salle_de_sport;
import services.SalleService;
import services.AbonnementService;

import utils.YasmineDatabase;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {
    public static void main(String [] args) throws ParseException {
        //YasmineDatabase d=YasmineDatabase.getInstance();
       SalleService ss=new SalleService();
      try {
            ss.ajouter_salle(new Salle_de_sport("salle ","hhh", Salle_de_sport.EnumRegion.Monastir,"image","adresse salle"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

       /* try {
            ss.modifier_salle(new Salle_de_sport(6,"califoniamodifr","desciptionodiifeiir", Salle_de_sport.EnumRegion.Tunis,"image","adresse salle"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/

        /*try {
            ss.supprimer_salle(4);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        /*try {
            System.out.println(ss.recuperer_salles());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
       AbonnementService aa=new AbonnementService();

       /* try {
            try {
                aa.ajouter_abonnement(new Abonnement(5,"3mois",35,new SimpleDateFormat("dd/MM/yyyy").parse("03/05/2023"), new SimpleDateFormat("dd/MM/yyyy").parse("08/05/2023")));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/



      /*  try {
            aa.modifier_abonnement(new Abonnement(1,5,"duree modifieee",100,new SimpleDateFormat("dd/MM/yyyy").parse("03/05/2027"),new SimpleDateFormat("dd/MM/yyyy").parse("03/05/2027")));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
/*
        try {
            aa.supprimer_abonnement(2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/

       /* try {
            System.out.println(aa.recuperer_abonnements());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
    }
}
