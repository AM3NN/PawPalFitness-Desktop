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
import utils.MyDabase;
import java.io.IOException;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

public class ForgotPassword {

    @FXML
    private TextField emailField;

    private Connection connection = null;
    private String email;

    public ForgotPassword() {
        connection = MyDabase.getInstance().getConnection();
    }

    private boolean isEmailRegistered(String email) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT COUNT(*) FROM personne WHERE email = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean storeVerificationCode(String email, String code) {
        PreparedStatement preparedStatement = null;

        try {
            String sql = "INSERT INTO verification_codes (email, code) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, code);

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

    @FXML
    void handleForgotPassword(ActionEvent event) {
        this.email = emailField.getText();

        if (this.email.isEmpty()) {
            showAlert("Please enter your email address.");
            return;
        }
        String nom = "";
        String prenom = "";
        try {
            String sql = "SELECT nom, prenom FROM personne WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                nom = resultSet.getString("nom");
                prenom = resultSet.getString("prenom");
            } else {
                showAlert("Email address not found.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("An error occurred. Please try again later.");
            return;
        }

        String generatedCode = generateCode();
        boolean emailSent = sendEmail(this.email, generatedCode, nom, prenom);

        if (emailSent) {
            boolean codeStored = storeVerificationCode(this.email, generatedCode);
            if (codeStored) {
                showAlert("Verification code sent successfully to your email.");
                initializeAndLoadVerifyCodeInterface();
            } else {
                showAlert("Failed to store verification code. Please try again later.");
            }
        } else {
            showAlert("Failed to send verification code. Please try again later.");
        }
    }


    private void initializeAndLoadVerifyCodeInterface() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VerifyCode.fxml"));
            Parent verifyCodeRoot = loader.load();
            VerifyCode verifyCodeController = loader.getController();
            verifyCodeController.initializeData(connection, email);
            Stage primaryStage = (Stage) emailField.getScene().getWindow();
            primaryStage.setScene(new Scene(verifyCodeRoot));
            primaryStage.setTitle("Verify Code");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean sendEmail(String recipientEmail, String verificationCode, String nom, String prenom) {
        final String EMAIL_USERNAME = "amenallah.laouini@esprit.tn";
        final String EMAIL_PASSWORD = "amen$$12345";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Password Reset Code");
            String emailBody = "Dear " + nom + " " + prenom + ",\n\n";
            emailBody += "Your verification code is: " + verificationCode + "\n\n";
            emailBody += "Best regards,\nPawPal Fitness\n\n";
            emailBody += "This is an automated email. Please do not reply to this email.";

            message.setText(emailBody);
            Transport.send(message);

            System.out.println("Email sent successfully.");
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email.");
            return false;
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Forgot Password");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void signin(ActionEvent actionEvent) {
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
}
