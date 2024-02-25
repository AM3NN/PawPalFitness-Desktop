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

public class Back {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Hyperlink btn_home;

    @FXML
    private Button btn_users;

    @FXML
    private Button btn_workers;

    @FXML
    void CheckUsers(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPersonne.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("afficher simple utilisateur");
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
            primaryStage.setTitle("afficher Travailleur");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
}


