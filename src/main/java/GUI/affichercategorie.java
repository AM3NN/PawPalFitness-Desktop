package GUI;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Animal;
import models.Categorie;
import services.AnimalService;
import services.CategorieService;

public class affichercategorie {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_animaux;

    @FXML
    private Hyperlink btn_home;

    @FXML
    private Button btn_produit;

    @FXML
    private Button btn_produit1;

    @FXML
    private Button btn_reservations;

    @FXML
    private Button btn_users;

    @FXML
    private Button btn_workers;

    @FXML
    private TableColumn<Categorie, String> nom;

    @FXML
    private Button supp;

    @FXML
    private TableView<Categorie> table;

    @FXML
    void Abonnements(ActionEvent event) {

    }

    @FXML
    void CheckUsers(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPersonne.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Afficher Simple Utilisateur");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void CheckWorkers(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherTravailleur.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Afficher Travailleur");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Logout(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Sign In");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Supprimer(ActionEvent event) throws SQLException {
        Categorie selectedCategorie = table.getSelectionModel().getSelectedItem();
        if (selectedCategorie != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Supprimer Categorie");
            alert.setContentText("Voulez vous vraiment supprimer cette categorie");
            Optional<ButtonType> resultat = alert.showAndWait();
            if (resultat.isPresent()&& resultat.get()==ButtonType.OK){
                CategorieService cs = new CategorieService();
                cs.supprimerCategorie(selectedCategorie.getNomc());
                table.getItems().remove(selectedCategorie);
            }
        }
    }

    @FXML
    void animaux(ActionEvent event) {

    }

    @FXML
    void produit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RecupererProduitAdmin.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Produit");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void reservation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReservation.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Reservation");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCellValue(){
        nom.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getNomc()));
    }

    @FXML
    void initialize() {
        setCellValue();
        CategorieService cs = new CategorieService();
        List<Categorie> categ = null;
        try {
            categ = cs.afficherCategorie();
            table.getItems().setAll(categ);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

