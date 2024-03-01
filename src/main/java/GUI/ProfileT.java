package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.MyDabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileT {

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
            preparedStatement = connection.prepareStatement("SELECT * FROM travailleur WHERE id = ?");
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                nomt_modif.setText(resultSet.getString("nom"));
                prenomt_modif.setText(resultSet.getString("prenom"));
                aget_modif.setText(resultSet.getString("age"));
                regiont_modif.setText(resultSet.getString("region"));
                emailt_modif.setText(resultSet.getString("email"));
                diplomet_modif.setText(resultSet.getString("diplome"));
                experiencet_modif.setText(resultSet.getString("experience"));
                languet_modif.setText(resultSet.getString("langue"));
                categoriet_modif.setText(resultSet.getString("categorie"));

                // Set the password field with the real password
                passwordt_modif.setText(resultSet.getString("password"));

                // Similarly, set other fields based on your database columns
            } else {
                showAlert("Travailleur not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error occurred while fetching travailleur information.");
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
    void CheckProfile(ActionEvent event) {
        populateProfile(userId);
    }

    @FXML
    void Home(ActionEvent event) {
        // Redirect to the home interface
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close(); // Close the current window
        // Add your code here to open the home interface
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
}
