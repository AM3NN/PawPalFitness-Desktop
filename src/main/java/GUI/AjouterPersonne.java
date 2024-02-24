package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Personne;
import services.PersonneService;

import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.Hyperlink;

public class AjouterPersonne {
    @FXML
    private Hyperlink signin_acc;
    @FXML
    private TextField nom_id;

    @FXML
    private TextField prenom_id;

    @FXML
    private TextField age_id;

    @FXML
    private TextField region_id;

    @FXML
    private TextField email_id;

    @FXML
    private TextField password_id;


    @FXML
    void addPerson(ActionEvent event) {
        String nom = nom_id.getText();
        String prenom = prenom_id.getText();
        String region = region_id.getText();
        String email = email_id.getText();
        String password = password_id.getText();
        int roleId;
        roleId = 2;
        try {
            int age = Integer.parseInt(age_id.getText());
            Personne personne = new Personne(age, nom, prenom, region, email, password, roleId);
            PersonneService ps = new PersonneService();
            ps.ajouter(personne);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Person added successfully!");
            alert.showAndWait();

            // Redirect to sign-in page
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
            alert.setContentText("Failed to add person: " + e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void showPersons(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPersonne.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("afficher personne");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
