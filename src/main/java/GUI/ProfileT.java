package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.MyDabase;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class ProfileT {

    @FXML
    private Button SU_Animal;
    private int userId;

    @FXML
    private TextField aget_modif;

    @FXML
    private Hyperlink btn_home;

    @FXML
    private TextField categoriet_modif;

    @FXML
    private TextField diplomet_modif;

    @FXML
    private TextField emailt_modif;

    @FXML
    private TextField experiencet_modif;

    @FXML
    private TextField languet_modif;

    @FXML
    private TextField nomt_modif;

    @FXML
    private TextField prenomt_modif;

    @FXML
    private TextField regiont_modif;

    @FXML
    private TextField passwordt_modif;

    public void setUserId(int userId) {
        this.userId = userId;
        populateProfile(userId);
    }

    private void populateProfile(int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = MyDabase.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT t.*, p.nom AS p_nom, p.prenom AS p_prenom, p.age AS p_age, " +
                            "p.region AS p_region, p.email AS p_email, t.diplome, t.experience, " +
                            "t.langue, t.categorie, p.password " +
                            "FROM travailleur t " +
                            "INNER JOIN personne p ON t.personne_id = p.id " +
                            "WHERE t.id = ?");
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Assuming 'id' is the column representing the travailleur ID
                int travailleurId = resultSet.getInt("id");
                nomt_modif.setText(resultSet.getString("p_nom"));
                prenomt_modif.setText(resultSet.getString("p_prenom"));
                aget_modif.setText(resultSet.getString("p_age"));
                regiont_modif.setText(resultSet.getString("p_region"));
                emailt_modif.setText(resultSet.getString("p_email"));
                diplomet_modif.setText(resultSet.getString("diplome"));
                experiencet_modif.setText(resultSet.getString("experience"));
                languet_modif.setText(resultSet.getString("langue"));
                categoriet_modif.setText(resultSet.getString("categorie"));
                passwordt_modif.setText(resultSet.getString("password"));
            } else {
                showAlert("No travailleur found for ID: " + userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error occurred while fetching travailleur information.");
        } finally {
            // Close resources
        }
    }
    public void Produit(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduitUser.fxml"));
            Parent profileRoot = loader.load();
            ShoppingPage profileController = loader.getController();
            profileController.setUserId(userId);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(profileRoot));
            primaryStage.setTitle("produit");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void CheckProfile(ActionEvent event) {
        populateProfile(userId);
    }

    @FXML
    void Home(ActionEvent event) {
        try {
            int travailleurId = getTravailleurIdFromDatabase(userId);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeT.fxml"));
            Parent profileRoot = loader.load();
            HomeT profileController = loader.getController();
            profileController.setUserId(travailleurId);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(profileRoot));
            primaryStage.setTitle("Home");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Logout(ActionEvent event) {
        // Redirect to the sign-in interface
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close(); // Close the current window
        // Add your code here to open the sign-in interface
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Profile Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void modifierPerson(ActionEvent actionEvent) {
        // Get the updated information from the text fields
        String nom = nomt_modif.getText();
        String prenom = prenomt_modif.getText();
        String age = aget_modif.getText();
        String region = regiont_modif.getText();
        String email = emailt_modif.getText();
        String diplome = diplomet_modif.getText();
        String experience = experiencet_modif.getText();
        String langue = languet_modif.getText();
        String categorie = categoriet_modif.getText();
        String password = passwordt_modif.getText();

        // Hash the password
        String hashedPassword = hashPassword(password);

        // Update the travailleur information in the database
        try {
            Connection connection = MyDabase.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE travailleur t " +
                            "INNER JOIN personne p ON t.personne_id = p.id " +
                            "SET p.nom = ?, p.prenom = ?, p.age = ?, p.region = ?, p.email = ?, " +
                            "t.diplome = ?, t.experience = ?, t.langue = ?, t.categorie = ?, p.password = ? " +
                            "WHERE t.id = ?");
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, age);
            preparedStatement.setString(4, region);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, diplome);
            preparedStatement.setString(7, experience);
            preparedStatement.setString(8, langue);
            preparedStatement.setString(9, categorie);
            preparedStatement.setString(10, hashedPassword); // Use the hashed password
            preparedStatement.setInt(11, userId); // Assuming userId is accessible in this method
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                showAlert("Travailleur information updated successfully.");
            } else {
                showAlert("Failed to update travailleur information.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error occurred while updating travailleur information.");
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
   private int getTravailleurIdFromDatabase(int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int travailleurId = -1;

        try {
            connection = MyDabase.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT id FROM travailleur WHERE personne_id = ?");
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                travailleurId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (log it, show error message, etc.)
        } finally {
            // Close resources (if necessary)
        }

        return travailleurId;
    }
    public void TypeAnimal(ActionEvent actionEvent) {
        try {
            int travailleurId = getTravailleurIdFromDatabase(userId);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TypeAnimal1.fxml"));
            Parent profileRoot = loader.load();
            TypeAnimal1 profileController = loader.getController();
            profileController.setUserId(travailleurId);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(profileRoot));
            primaryStage.setTitle("Home");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

