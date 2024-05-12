package GUI;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import utils.MyDabase;

public class ChangePassword {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private PasswordField newPasswordField;

    private String email;
    private Connection connection;

    public void initializeData(String email) {
        this.email = email;
        this.connection = MyDabase.getInstance().getConnection();
    }

    @FXML
    void changePassword(ActionEvent event) {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Check if new password matches the confirmed password
        if (!newPassword.equals(confirmPassword)) {
            showAlert("Passwords do not match. Please try again.");
            return;
        }

        // Hash the new password before storing it in the database
        String hashedPassword = hashPassword(newPassword);

        // Update hashed password in the database
        if (updatePasswordInDatabase(hashedPassword)) {
            showAlert("Password changed successfully.");
            redirectToSignInInterface();
        } else {
            showAlert("Failed to change password. Please try again later.");
        }
    }

    private boolean updatePasswordInDatabase(String hashedPassword) {
        try {
            String sql = "UPDATE personne SET password = ? WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, hashedPassword);
            preparedStatement.setString(2, email);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Change Password");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        // Initialize the controller
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

    private void redirectToSignInInterface() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/signin.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) confirmPasswordField.getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Sign In");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
