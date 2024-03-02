package GUI;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.scene.text.Text;

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
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import utils.MyDabase;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class SignIn {
    public Pane captchaLabel1;
    public MFXTextField inputField;
    @FXML
    private WebView recaptchaWebView;
    @FXML
    private Pane captchaLabel;
    @FXML
    private WebEngine recaptchaWebEngine;

    @FXML
    private Hyperlink ForgotPwdLabel;

    @FXML
    private Hyperlink SignUp;

    @FXML
    private TextField email_signin;

    @FXML
    private Button login_btn;
    private String captchaValue;

    @FXML
    private PasswordField password_signin;

    @FXML
    void initialize() {
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/gothicb.ttf"), 72);
        Font customFont2 = Font.loadFont(getClass().getResourceAsStream("/gothicb.ttf"), 18);
        generateCaptcha();
        setCaptcha();

    }

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForgotPassword.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("SignUp");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void login(ActionEvent event) {
        String email = email_signin.getText();
        String password = password_signin.getText();
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Please enter both email and password.");
            return;
        }
        String enteredCaptcha = inputField.getText(); // Get the entered captcha

        if (!enteredCaptcha.equals(captchaValue)) {
            showAlert("Invalid captcha. Please try again.");
            return;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = MyDabase.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT id, password, role_id FROM personne WHERE email = ?");
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
                int roleId = resultSet.getInt("role_id"); // Retrieve the role ID
                String hashedPasswordFromDB = resultSet.getString("password");
                String hashedPassword = hashPassword(password);
                if (hashedPassword.equals(hashedPasswordFromDB)) {
                    String homeFXML = "/Home.fxml";
                    if (roleId == 3) { // If role_id is 3 (travailleur), redirect to HomeT.fxml
                        homeFXML = "/HomeT.fxml";
                    }
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(homeFXML));
                    Parent homeRoot = loader.load();
                    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    primaryStage.setScene(new Scene(homeRoot, 800, 550));
                    primaryStage.setTitle("Home");

                    // Get the controller based on the loaded FXML
                    if (roleId == 3) {
                        HomeT homeController = loader.getController();
                        homeController.setUserId(userId);
                    } else {
                        Home homeController = loader.getController();
                        homeController.setUserId(userId);
                    }

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

    private void generateCaptcha() {
        StringBuilder valueBuilder = new StringBuilder();
        Random random = new Random();

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        for (int i = 0; i < 6; i++) {
            char charValue = characters.charAt(random.nextInt(characters.length()));
            valueBuilder.append(charValue).append(" ");
        }

        captchaValue = valueBuilder.toString().trim();
    }

    private void setCaptcha() {
        captchaLabel.getChildren().clear();

        double xPos = 0;

        String[] fonts = {"cursive", "sans-serif", "serif", "monospace"};

        for (char charValue : captchaValue.toCharArray()) {
            Text text = new Text(String.valueOf(charValue));
            int rotate = -20 + new Random().nextInt(30);
            int padding = new Random().nextInt(10) + 5;

            String randomColor = String.format("#%06X", new Random().nextInt(0xFFFFFF));

            // Randomly select a font from the 'fonts' array
            String randomFont = fonts[new Random().nextInt(fonts.length)];

            text.setStyle("-fx-rotate: " + rotate + "; -fx-font-family: '" + randomFont + "'; -fx-font-size: 26; -fx-fill: " + randomColor + ";");

            text.setTranslateX(xPos);
            text.setTranslateY(0);

            captchaLabel.getChildren().add(text);

            xPos += text.getBoundsInLocal().getWidth() + padding;
        }
    }

    @FXML
    public void refreshCaptcha() {
        generateCaptcha();
        setCaptcha();
    }

}
