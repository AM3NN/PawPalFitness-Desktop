package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.MyDabase;

public class HomeT {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button SU_Animal;

    @FXML
    private Hyperlink btn_home;

    @FXML
    private Button btn_profile;
    private int userId;

    @FXML
    void CheckProfile(ActionEvent event) {
        try {
            // Assuming you have access to the travailleur ID in this controller
            int travailleurId = getTravailleurIdFromDatabase(userId); // Method to fetch the travailleur ID from the database

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProfileT.fxml"));
            Parent profileRoot = loader.load();
            ProfileT profileController = loader.getController();
            profileController.setUserId(travailleurId);
            System.out.println("Travailleur ID before setting: " + travailleurId); // Print the travailleur ID

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(profileRoot));
            primaryStage.setTitle("Profile");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getTravailleurIdFromDatabase(int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int travailleurId = -1; // Default value in case no travailleur ID is found

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

    @FXML
    void Home(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeT.fxml"));
            Parent profileRoot = loader.load();
            Profile profileController = loader.getController();
            profileController.setUserId(userId);
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
        // Add logout functionality here
    }

    @FXML
    void initialize() {
        // Initialize any components or actions here
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
