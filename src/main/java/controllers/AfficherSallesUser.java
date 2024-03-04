package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Salle_de_sport;
import services.SalleService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherSallesUser {
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
}
