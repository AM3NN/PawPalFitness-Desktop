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
import java.security.MessageDigest;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
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

        // Regular expression pattern for a valid email address
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        // Check if email matches the pattern
        if (!email.matches(emailPattern)) {
            showAlert(Alert.AlertType.ERROR, "Error", null, "Please enter a valid email address.");
            return;
        }

        // Check if nom and prenom contain only letters and spaces
        String namePattern = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\s]+$";
        if (!nom.matches(namePattern) || !prenom.matches(namePattern)) {
            showAlert(Alert.AlertType.ERROR, "Error", null, "Nom and prenom should contain only letters and spaces.");
            return;
        }

        try {
            int age = Integer.parseInt(age_t.getText());

            // Check if age is a positive integer
            if (age <= 0) {
                showAlert(Alert.AlertType.ERROR, "Error", null, "Age must be a positive integer.");
                return;
            }

            String hashedPassword = hashPassword(password);
            Travailleur travailleur = new Travailleur(age, nom, prenom, region, email, hashedPassword, roleId, diplome, categorie, langue, experience);
            TravailleurService ts = new TravailleurService();
            if (ts.travailleurExists(email)) {
                showAlert(Alert.AlertType.ERROR, "Error", null, "A worker with this email already exists.");
                return;
            }

            ts.ajouter(travailleur);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Worker added successfully!");
            alert.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Sign In");
            primaryStage.show();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", null, "Invalid age format: " + e.getMessage());
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", null, "Failed to add worker: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null; // Handle error accordingly
        }
    }



    public void showTravailleur(ActionEvent actionEvent) {



    }

    public void SignIn(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("SignIn");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    }



