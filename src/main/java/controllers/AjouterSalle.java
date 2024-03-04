package controllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Salle_de_sport;
import services.SalleService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AjouterSalle {

    private final SalleService salleService; // Create an instance variable for SalleService

    public AjouterSalle() {
        salleService = new SalleService(); // Initialize SalleService in the constructor
    }

    @FXML
    private TextField nomSalleField;

    @FXML
    private TextField descriptionField;

    @FXML
    private ComboBox<String> regionComboBox;

    @FXML
    private TextField imageField;

    @FXML
    private TextField adresseField;
    @FXML
    private ImageView imageView;

    public void initialize() {
        // Ajouter chaque valeur de l'énumération EnumRegion au ComboBox
        for (Salle_de_sport.EnumRegion region : Salle_de_sport.EnumRegion.values()) {
            regionComboBox.getItems().add(region.toString());
        }
    }

    public void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers Image", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            imageField.setText(selectedFile.getAbsolutePath());
            // Charger l'image dans l'ImageView
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        }
    }
    public static Salle_de_sport.EnumRegion convertToEnumRegion(String regionString) {
        return Salle_de_sport.EnumRegion.valueOf(regionString); // Cela suppose que les noms des régions sont exactement les mêmes que les noms des EnumRegion
    }

    @FXML
    public void ajouter_Salle() {
        try {
            String nomSalle = nomSalleField.getText();
            String description = descriptionField.getText();
            String image = imageField.getText();
            String adresse = adresseField.getText();

            String regionString = regionComboBox.getValue();
            Salle_de_sport.EnumRegion region = Salle_de_sport.convertToEnumRegion(regionString);

            if (regionString == null || regionString.isEmpty()) {
                throw new IllegalArgumentException("Veuillez sélectionner une région.");
            }

            if (nomSalle.isEmpty() || description.isEmpty() || image.isEmpty() || adresse.isEmpty()) {
                throw new IllegalArgumentException("Tous les champs doivent être remplis.");
            }

            Salle_de_sport salle_de_sport = new Salle_de_sport(nomSalle, description, region, image, adresse);

            // Call ajouter_salle on the instance of SalleService
            salleService.ajouter_salle(salle_de_sport);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Salle ajoutée");
            alert.setHeaderText(null);
            alert.setContentText("La salle a été ajoutée avec succès !");
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            // Handle illegal argument exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors de l'ajout de la salle");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
            // Optionally, show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors de l'ajout de la salle");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de l'ajout de la salle. Veuillez réessayer.");
            alert.showAndWait();
        }
    }

    public void retourSalle(javafx.event.ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSallesAdmin.fxml"));
            Parent AjoutSallesRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(AjoutSallesRoot));
            primaryStage.setTitle("");
            primaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}