package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Salle_de_sport;

import java.io.IOException;

public class AfficherDetailsUser {
    @FXML
    private Label nomSalleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label regionLabel;

    @FXML
    private Label adresseLabel;

    @FXML
    private ImageView imageView;

    public void initData(Salle_de_sport salle) {
        nomSalleLabel.setText(salle.getNom_salle());
        descriptionLabel.setText(salle.getDescription_salle());
        regionLabel.setText(salle.getRegion_salle().toString());
        adresseLabel.setText(salle.getAdresse_salle());

        // Chargement de l'image
        if (salle.getImage_salle() != null && !salle.getImage_salle().isEmpty()) {
            Image image = new Image(salle.getImage_salle());
            imageView.setImage(image);
        }
    }

    public void retourSalle(javafx.event.ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSallesUser.fxml"));
            Parent afficherDetailsUserRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(afficherDetailsUserRoot));
            primaryStage.setTitle("");
            primaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
