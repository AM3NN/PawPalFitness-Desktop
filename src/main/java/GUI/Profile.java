package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Personne;
import services.PersonneService;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.MyDabase;

import java.util.Objects;
import java.util.Optional;


public class Profile {
    @FXML
    private Button SU_Animal;
    @FXML
    private TextField nom_mod;

    @FXML
    private TextField prenom_mod;

    @FXML
    private TextField age_mod;

    @FXML
    private TextField region_mod;

    @FXML
    private TextField email_mod;

    @FXML
    private PasswordField password_mod;

    private int userId;

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
            preparedStatement = connection.prepareStatement("SELECT * FROM personne WHERE id = ?");
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                nom_mod.setText(resultSet.getString("nom"));
                prenom_mod.setText(resultSet.getString("prenom"));
                age_mod.setText(resultSet.getString("age"));
                region_mod.setText(resultSet.getString("region"));
                email_mod.setText(resultSet.getString("email"));
                password_mod.setText(resultSet.getString("password"));
            } else {
                showAlert("User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error occurred while fetching user information.");
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    @FXML
    void Home(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
            Parent profileRoot = loader.load();
            Home profileController = loader.getController();
            profileController.setUserId(userId);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(profileRoot));
            primaryStage.setTitle("Home");
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
    void modifierPerson(ActionEvent actionEvent) {
        String nom = nom_mod.getText();
        String prenom = prenom_mod.getText();
        int age = Integer.parseInt(age_mod.getText());
        String region = region_mod.getText();
        String email = email_mod.getText();
        String password = password_mod.getText();
        String hashedPassword = hashPassword(password);
        Personne personne = new Personne();
        personne.setNom(nom);
        personne.setPrenom(prenom);
        personne.setAge(age);
        personne.setRegion(region);
        personne.setEmail(email);
        personne.setPassword(hashedPassword);

        try {
            int selectedPersonId = getIdOfSelectedPerson();
            if (selectedPersonId == -1) {
                showAlert("No person is selected.");
                return;
            }
            PersonneService personneService = new PersonneService();
            personne.setId(selectedPersonId);
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Modifier la personne");
            confirmationAlert.setContentText("Voulez-vous vraiment modifier la personne ?");
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.out.println("Calling PersonneService.modifier method");
                personneService.modifier(personne);
                System.out.println("PersonneService.modifier method called successfully");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Modification Successful");
                alert.setHeaderText(null);
                alert.setContentText("The modification was successful.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while modifying the person. Please try again later.");
            alert.showAndWait();
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


    private int getIdOfSelectedPerson() {
        return userId;
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Profile Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void CheckProfile(ActionEvent actionEvent) {
        populateProfile(userId);
    }

    public void TypeAnimal(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TypeAnimal.fxml"));
            Parent profileRoot = loader.load();
            TypeAnimal profileController = loader.getController();
            profileController.setUserId(userId);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(profileRoot));
            primaryStage.setTitle("Home");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
