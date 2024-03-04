package tests;

import controllers.AfficherSallesAdmin;
import controllers.AfficherSallesUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }

  /*  @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterSalle.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }*/

    //modifier
 /* public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierSalle.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }*/
   // AfficherSallesAdmin
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSallesAdmin.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

            // Obtenir le contrôleur AfficherSallesAdmin
            AfficherSallesAdmin controller = loader.getController();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    // AfficherSallesAdmin
   /* public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSallesUser.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

            // Obtenir le contrôleur AfficherSallesUser
            AfficherSallesUser controller = loader.getController();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }*/





}
