package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Travailleur;
import services.TravailleurService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class AjouterTravailleur {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField age_t;

    @FXML
    private Button btn_t;

    @FXML
    private TextField categorie_t;

    @FXML
    private TextField diplome_t;

    @FXML
    private TextField email_t;

    @FXML
    private TextField experience_t;

    @FXML
    private TextField langue_t;

    @FXML
    private TextField nom_t;

    @FXML
    private TextField password_t;

    @FXML
    private TextField prenom_t;

    @FXML
    private TextField region_t;
    private int roleId = 1;

    @FXML
    void addTravailleur(ActionEvent event) {
        String nom = nom_t.getText();
        String prenom = prenom_t.getText();
        String region = region_t.getText();
        String email = email_t.getText();
        String password = password_t.getText();
        String diplome = diplome_t.getText();
        String categorie = categorie_t.getText();
        String langue = langue_t.getText();
        String experience = experience_t.getText();
        roleId = 3;
        try {
            int age = Integer.parseInt(age_t.getText());
            Travailleur travailleur = new Travailleur(age, nom, prenom, region, email, password, roleId, diplome, categorie, langue, experience);
            TravailleurService ts = new TravailleurService();
            ts.ajouter(travailleur);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Travailleur added successfully!");
            alert.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Sign In");
            primaryStage.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid age format: " + e.getMessage());
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add travailleur: " + e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showTravailleur(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherTravailleur.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("afficher Travailleur");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }


