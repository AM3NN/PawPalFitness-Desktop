package GUI;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import utils.MyDabase;

public class backanimal {

    @FXML
    private ResourceBundle resources;

    @FXML
    private Button ajoutA;
    @FXML
    private Button suppA;

    @FXML
    private URL location;

    @FXML
    private Button btn_animaux;

    @FXML
    private Hyperlink btn_home;

    @FXML
    private Button btn_produit;

    @FXML
    private Button btn_produit1;

    @FXML
    private Button btn_reservations;

    @FXML
    private Button btn_users;

    @FXML
    private Button btn_workers;

    @FXML
    private LineChart<String, Number> statisticsChart;

    @FXML
    void initialize() {
        fetchStatistics();
    }

    @FXML
    void SupprimerA(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/affichercategorie.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Reservation");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ajouterA(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajoutcategorie.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Reservation");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void Abonnements(ActionEvent event) {

    }

    @FXML
    void CheckUsers(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPersonne.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Afficher Simple Utilisateur");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void CheckWorkers(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherTravailleur.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Afficher Travailleur");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Logout(ActionEvent event) {

    }

    @FXML
    void animaux(ActionEvent event) {

    }

    @FXML
    void produit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RecupererProduitAdmin.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Produit");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void reservation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReservation.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Reservation");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fetchStatistics() {
        try {
            Connection connection = MyDabase.getInstance().getConnection();
            Statement stmt = connection.createStatement();

            // Query to count the number of workers
            ResultSet chatResult = stmt.executeQuery("SELECT COUNT(*) FROM Categorie where nomc='Chat'");
            int totalChats = 0;
            if (chatResult.next()) {
                totalChats = chatResult.getInt(1);
            }

            // Query to count the number of simple users
            ResultSet chienResult = stmt.executeQuery("SELECT COUNT(*) FROM Categorie where nomc='Chien'");
            int totalChiens = 0;
            if (chienResult.next()) {
                totalChiens = chienResult.getInt(1);
            }

            // Create series for workers and users
            XYChart.Series<String, Number> chatSeries = new XYChart.Series<>();
            chatSeries.setName("Chats");
            chatSeries.getData().add(new XYChart.Data<>("Chats", totalChats));

            XYChart.Series<String, Number> chiensSeries = new XYChart.Series<>();
            chiensSeries.setName("Chiens");
            chiensSeries.getData().add(new XYChart.Data<>("Chiens", totalChiens));

            // Clear previous data and add series to the chart
            statisticsChart.getData().clear();
            statisticsChart.getData().add(chatSeries);
            statisticsChart.getData().add(chiensSeries);

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
