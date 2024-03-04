package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Salle_de_sport;
import services.SalleService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherSallesAdmin {

    @FXML
    private TableView<Salle_de_sport> tableView;

    @FXML
    private TableColumn<Salle_de_sport, Integer> idCol;

    @FXML
    private TableColumn<Salle_de_sport, String> nomCol;

    @FXML
    private TableColumn<Salle_de_sport, Salle_de_sport.EnumRegion> regionCol;


    @FXML
    private TableColumn<Salle_de_sport, String> adresseCol;

    /* @FXML
    private TableColumn<Salle_de_sport, String> imageCol;  */
    /* @FXML
    private TableColumn<Salle_de_sport, String> descriptionCol;  */
    @FXML
    private ComboBox<Salle_de_sport.EnumRegion> regionComboBox;

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
//filtres
        regionComboBox.setItems(FXCollections.observableArrayList(Salle_de_sport.EnumRegion.values()));

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

   /* private void afficherSalles(List<Salle_de_sport> salles) {
        // Nettoyer les données existantes
        tableView.getItems().clear();

        // Créer une ObservableList à partir de la liste de salles
        ObservableList<Salle_de_sport> observableSalles = FXCollections.observableArrayList(salles);

        // Assigner l'ObservableList à la TableView
        tableView.setItems(observableSalles);
    }*/


    public void ajouterNouvelleSalle(javafx.event.ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterSalle.fxml"));
            Parent AfficherSallesRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(AfficherSallesRoot));
            primaryStage.setTitle("Ajout Salle");
            primaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void ModifierSalle(ActionEvent event) {
        // Récupérer la salle sélectionnée dans la TableView
        Salle_de_sport salleSelectionnee = tableView.getSelectionModel().getSelectedItem();

        if (salleSelectionnee != null) {
            try {
                // Charger l'interface ModifierSalle
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierSalle.fxml"));
                Parent modifierSalleParent = loader.load();
                ModifierSalle modifierSalleController = loader.getController();

                // Passer les données de la salle sélectionnée au contrôleur de l'interface ModifierSalle
                modifierSalleController.initData(salleSelectionnee);

                // Afficher l'interface ModifierSalle dans une nouvelle fenêtre ou une boîte de dialogue
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setScene(new Scene(modifierSalleParent));
                primaryStage.setTitle("Modification Salle");
                primaryStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Aucune salle selectionnée. Veuillez selectionner une salle!");
            alert.showAndWait();
        }
    }

    @FXML
    private void SupprimerSalle(ActionEvent event) {
        // Récupérer la salle sélectionnée dans la TableView
        Salle_de_sport salleSelectionnee = tableView.getSelectionModel().getSelectedItem();

        if (salleSelectionnee != null) {
            try {
                // Appeler la méthode de service pour supprimer la salle
                SalleService salleService = new SalleService();
                salleService.supprimer_salle(salleSelectionnee.getId_salle());

                // Actualiser la TableView après la suppression
                tableView.getItems().remove(salleSelectionnee);
            } catch (SQLException e) {
                // Gérer les erreurs de suppression
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Aucune salle selectionnée. Veuillez selectionner une salle!");
            alert.showAndWait();
        }
    }

    public void Map(javafx.event.ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MapSalle.fxml"));
            Parent MapSallesRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(MapSallesRoot));
            primaryStage.setTitle("Map");
            primaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void afficherDetails(ActionEvent event) throws IOException {
        // Récupérer la salle sélectionnée dans la TableView
        Salle_de_sport salleSelectionnee = tableView.getSelectionModel().getSelectedItem();

        if (salleSelectionnee != null) {
            try {
                // Charger l'interface AfficherDetailsSalle
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherDetailsSalle.fxml"));
                Parent afficherDetailsSalleParent = loader.load();
                AfficherDetailsSalle afficherDetailsSalleController = loader.getController();

                // Passer les données de la salle sélectionnée au contrôleur de l'interface AfficherDetailsSalle
                afficherDetailsSalleController.initData(salleSelectionnee);

                // Afficher l'interface AfficherDetailsSalle dans une nouvelle fenêtre ou une boîte de dialogue
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setScene(new Scene(afficherDetailsSalleParent));
                primaryStage.setTitle("Details de la salle");
                primaryStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Aucune salle selectionnée. Veuillez selectionner une salle!");
            alert.showAndWait();
        }
    }



    //filtrer
    /*
    @FXML
    private void filtrerParRegion(ActionEvent event) {
        // Récupérez la région sélectionnée dans le ComboBox
        Salle_de_sport.EnumRegion regionSelectionnee = regionComboBox.getValue();

        // Vérifiez si une région a été sélectionnée
        if (regionSelectionnee != null) {
            try {
                // Utilisez la région sélectionnée pour filtrer la liste des salles
                List<Salle_de_sport> sallesFiltrees = salleService.recuperer_salles_par_region(regionSelectionnee);

                // Effacez la TableView et ajoutez les salles filtrées
                tableView.getItems().clear();
                tableView.getItems().addAll(sallesFiltrees);
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérez les erreurs de récupération des données
            }
        } else {
            // Affichez un message si aucune région n'a été sélectionnée
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une région.");
            alert.showAndWait();
        }
    }*/
}
