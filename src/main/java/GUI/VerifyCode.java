package GUI;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.MyDabase;

public class VerifyCode {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField verificationCodeField;

    private String email;
    private Connection connection;

    // Method to initialize the database connection and email
    public void initializeData(Connection connection, String email) {
        this.connection = connection;
        this.email = email;
    }

    @FXML
    void verifyCode(ActionEvent event) {
        String enteredCode = verificationCodeField.getText().trim();

        // Ensure the connection is initialized before performing database operations
        if (connection == null) {
            showError("Database connection is not initialized.");
            return;
        }

        // Retrieve the verification code from the database
        String expectedCode = retrieveVerificationCode();

        if (expectedCode == null) {
            showError("Failed to retrieve verification code.");
            return;
        }

        // Debug: Print retrieved and entered codes
        System.out.println("Entered Code: " + enteredCode);
        System.out.println("Expected Code: " + expectedCode);

        // Check if the entered code matches the expected verification code
        if (enteredCode.equals(expectedCode)) {
            showAlert("Verification successful!");
            // Proceed with the password change process
            redirectToChangePasswordInterface();
            // Delete the verification code from the database
            deleteVerificationCode();
        } else {
            showError("Invalid verification code. Please try again.");
        }
    }

    private void redirectToChangePasswordInterface() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChangePassword.fxml"));
            Parent changePasswordRoot = loader.load();
            ChangePassword changePasswordController = loader.getController();
            changePasswordController.initializeData(email); // Pass the email
            Stage primaryStage = (Stage) verificationCodeField.getScene().getWindow(); // Accessing the scene from verificationCodeField
            primaryStage.setScene(new Scene(changePasswordRoot));
            primaryStage.setTitle("Change Password");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve the verification code from the database
    private String retrieveVerificationCode() {
        try {
            String sql = "SELECT code FROM verification_codes WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String code = resultSet.getString("code");
                // Debug: Print retrieved code
                System.out.println("Retrieved Code: " + code);
                return code;
            } else {
                // Debug: Print message if no data found for the email
                System.out.println("No verification code found for email: " + email);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving verification code: " + e.getMessage());
            return null;
        }
    }

    // Method to delete the verification code from the database
    private void deleteVerificationCode() {
        try {
            String sql = "DELETE FROM verification_codes WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Verification code deleted successfully.");
            } else {
                System.out.println("No verification code found to delete.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error deleting verification code: " + e.getMessage());
        }
    }

    @FXML
    void initialize() {
        // Initialize the UI
    }

    // Method to show an alert
    private void showAlert(String message) {
        errorLabel.setText(message);
    }

    // Method to show an error message
    private void showError(String message) {
        errorLabel.setText(message);
    }
}
