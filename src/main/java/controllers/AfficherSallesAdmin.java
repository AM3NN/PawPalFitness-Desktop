package controllers;

import com.sun.javafx.charts.Legend;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import models.Salle_de_sport;
import services.SalleService;

import java.sql.SQLException;
import java.util.List;

public class AfficherSallesAdmin {
    @FXML
    private TableView<Salle_de_sport> tableView;

    @FXML
    private TableColumn<Salle_de_sport, Integer> idCol;

    @FXML
    private TableColumn<Salle_de_sport, String> nomCol;

   /* @FXML
    private TableColumn<Salle_de_sport, String> descriptionCol;  */

    @FXML
    private TableColumn<Salle_de_sport, Salle_de_sport.EnumRegion> regionCol;

   /* @FXML
    private TableColumn<Salle_de_sport, String> imageCol;  */

    @FXML
    private TableColumn<Salle_de_sport, String> adresseCol;

    private final SalleService salleService = new SalleService();




    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id_salle"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom_salle"));
     //   descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description_salle"));
        regionCol.setCellValueFactory(new PropertyValueFactory<>("region_salle"));
      //  imageCol.setCellValueFactory(new PropertyValueFactory<>("image_salle"));
        adresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse_salle"));






        // Charger les données des salles dans la TableView
        recupererSalles();
    }








    private void recupererSalles() {
        try {
            List<Salle_de_sport> salles = salleService.recuperer_salles(); // Utiliser l'instance salleService
            tableView.getItems().addAll(salles);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de chargement des données
        }
    }

    private void afficherSalles(List<Salle_de_sport> salles) {
        // Nettoyer les données existantes
        tableView.getItems().clear();

        // Créer une ObservableList à partir de la liste de salles
        ObservableList<Salle_de_sport> observableSalles = FXCollections.observableArrayList(salles);

        // Assigner l'ObservableList à la TableView
        tableView.setItems(observableSalles);
    }


}
