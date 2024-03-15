package GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utils.MyDabase;

public class Back {
    @FXML
    private Label workersCountLabel;

    @FXML
    private Label personsCountLabel;

    @FXML
    private LineChart<String, Number> statisticsChart;

    @FXML
    private Hyperlink btn_home;

    @FXML
    private Button btn_users;

    @FXML
    private Button btn_workers;

    @FXML
    void initialize() {
        fetchStatistics();
    }

    private void fetchStatistics() {
        try {
            Connection connection = MyDabase.getInstance().getConnection();
            Statement stmt = connection.createStatement();

            // Query to count the number of workers
            ResultSet workersResult = stmt.executeQuery("SELECT COUNT(*) FROM travailleur");
            int totalWorkers = 0;
            if (workersResult.next()) {
                totalWorkers = workersResult.getInt(1);
                workersCountLabel.setText("Number of Workers: " + totalWorkers);
            }

            // Query to count the number of simple users
            ResultSet personsResult = stmt.executeQuery("SELECT COUNT(*) FROM personne");
            int totalPersons = 0;
            if (personsResult.next()) {
                totalPersons = personsResult.getInt(1);
                personsCountLabel.setText("Number of Persons: " + totalPersons);
            }

            // Create series for workers and users
            XYChart.Series<String, Number> workerSeries = new XYChart.Series<>();
            workerSeries.setName("Workers");
            workerSeries.getData().add(new XYChart.Data<>("Workers", totalWorkers));

            XYChart.Series<String, Number> personSeries = new XYChart.Series<>();
            personSeries.setName("Persons");
            personSeries.getData().add(new XYChart.Data<>("Persons", totalPersons));

            // Add series to the chart
            statisticsChart.getData().addAll(workerSeries, personSeries);

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public void Logout(ActionEvent actionEvent) {
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

    public void reservation(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReservation.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Reservation");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void produit(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RecupererProduitAdmin.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Produit");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void Abonnements(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSallesAdmin.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Produit");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
