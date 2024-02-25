package GUI;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.MyDabase;

public class SignIn {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Hyperlink ForgotPwdLabel;

    @FXML
    private Hyperlink SignUp;

    @FXML
    private TextField email_signin;

    @FXML
    private Button login_btn;

    @FXML
    private PasswordField password_signin;

    @FXML
    void SignUp(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUp.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("SignUp");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void textfieldDesign() {
        if (this.email_signin.isFocused()) {
            this.email_signin.setStyle("-fx-background-color:#fff;-fx-border-width:2px");
            this.password_signin.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
        } else if (this.password_signin.isFocused()) {
            this.email_signin.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.password_signin.setStyle("-fx-background-color:#fff;-fx-border-width:2px");
        }
    }

    @FXML
    void forgotPassword(ActionEvent event) {
    }

    @FXML
    void login(ActionEvent event) {
        String email = email_signin.getText();
        String password = password_signin.getText();
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Please enter both email and password.");
            return;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = MyDabase.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT id, password FROM personne WHERE email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (email.equals("amenallah.laouini@esprit.tn") && password.equals("1234")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.fxml"));
                Parent homeRoot = loader.load();
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setScene(new Scene(homeRoot, 800, 550));
                primaryStage.setTitle("Back");
                primaryStage.show();
                showAlert("Welcome Admin!");
            } else if (resultSet.next()) {
                int userId = resultSet.getInt("id"); // Retrieve the user ID
                String hashedPasswordFromDB = resultSet.getString("password");
                String hashedPassword = hashPassword(password);
                if (hashedPassword.equals(hashedPasswordFromDB)) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
                    Parent homeRoot = loader.load();
                    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    primaryStage.setScene(new Scene(homeRoot, 800, 550));
                    primaryStage.setTitle("Home");

                    // Pass the user ID to the controller of the next scene
                    Home homeController = loader.getController();
                    homeController.setUserId(userId);

                    primaryStage.show();

                    showAlert("Login successful!");
                } else {
                    showAlert("Invalid email or password. Please try again.");
                }
            } else {
                showAlert("Invalid email or password. Please try again.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("Error occurred during login. Please try again later.");
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void textfieldDesign(MouseEvent event) {
        // Implement textfieldDesign for mouse event if needed
    }

    @FXML
    void initialize() {
        // Initialization logic if needed
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
}
