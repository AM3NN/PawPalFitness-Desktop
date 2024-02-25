package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Personne;
import services.PersonneService;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        if (nom_id.getText().isEmpty() || prenom_id.getText().isEmpty() || age_id.getText().isEmpty() ||
                region_id.getText().isEmpty() || email_id.getText().isEmpty() || password_id.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", null, "Please fill in all the required fields.");
            return;
        }
        String namePattern = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\s\\-]+$";
        if (!nom_id.getText().matches(namePattern)) {
            showAlert(Alert.AlertType.ERROR, "Error", null, "Please enter a valid name without special symbols.");
            return;
        }
        if (!prenom_id.getText().matches(namePattern)) {
            showAlert(Alert.AlertType.ERROR, "Error", null, "Please enter a valid name without special symbols.");
            return;
        }

        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        if (!email_id.getText().matches(emailPattern)) {
            showAlert(Alert.AlertType.ERROR, "Error", null, "Please enter a valid email address.");
            return;
        }
        try {
            PersonneService ps = new PersonneService();
            if (ps.personneExists(email_id.getText())) {
                showAlert(Alert.AlertType.ERROR, "Error", null, "A person with this email already exists.");
                return;
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", null, "Error checking person existence: " + e.getMessage());
            return;
        }
        String nom = nom_id.getText();
        String prenom = prenom_id.getText();
        String region = region_id.getText();
        String email = email_id.getText();
        String password = password_id.getText();
        int roleId = 2;
        try {
            int age = Integer.parseInt(age_id.getText());
            if (age < 0) {
                showAlert(Alert.AlertType.ERROR, "Error", null, "Age cannot be negative.");
                return;
            }
            String hashedPassword = hashPassword(password);
            Personne personne = new Personne(age, nom, prenom, region, email, hashedPassword, roleId);
            PersonneService ps = new PersonneService();
            ps.ajouter(personne);
            showAlert(Alert.AlertType.INFORMATION, "Information", null, "Person added successfully!");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Sign In");
            primaryStage.show();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", null, "Invalid age format: " + e.getMessage());
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", null, "Failed to add person: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            return null;
        }
    }
    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void SignIn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("SignIn");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
