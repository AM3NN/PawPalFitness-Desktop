package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Home {
    @FXML
    private Button SU_Animal;
    @FXML
    private Button chatButton;
    private int userId;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {

    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @FXML
    void CheckProfile(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
            Parent profileRoot = loader.load();
            Profile profileController = loader.getController();
            profileController.setUserId(userId);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(profileRoot));
            primaryStage.setTitle("Profile");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Logout(ActionEvent actionEvent) {
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

    @FXML
    void Home(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
            Parent profileRoot = loader.load();
            Profile profileController = loader.getController();
            profileController.setUserId(userId);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(profileRoot));
            primaryStage.setTitle("Home");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void TypeAnimal(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TypeAnimal.fxml"));
            Parent profileRoot = loader.load();
            TypeAnimal profileController = loader.getController();
            profileController.setUserId(userId);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(profileRoot));
            primaryStage.setTitle("Home");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Planning(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShoppingPage.fxml"));
            Parent profileRoot = loader.load();
            ShoppingPage shoppingPageController = loader.getController();
            shoppingPageController.setUserId(userId);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(profileRoot));
            primaryStage.setTitle("Home");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void produit(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduitUser.fxml"));
            Parent profileRoot = loader.load();
            AfficherProduitUser shoppingPageController = loader.getController();
            shoppingPageController.setUserId(userId);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(profileRoot));
            primaryStage.setTitle("Home");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void chatbot(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/chatbot.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("ChatBot");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
