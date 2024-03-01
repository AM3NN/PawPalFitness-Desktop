package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Salle_de_sport;
import services.SalleService;

import java.awt.*;
import java.io.File;
import java.sql.SQLException;

public class ModifierSalle {
    private SalleService salleService; // Create an instance variable for SalleService


    @FXML
    private TextField idSalleField;

    @FXML
    private TextField nomSalleField;

    @FXML
    private TextField descriptionField;

    @FXML
    private ComboBox<String> regionComboBox;

    @FXML
    private TextField adresseField;

    @FXML
    private TextField imageField;

    @FXML
    private ImageView imageView;

    public ModifierSalle(SalleService salleService) {
        this.salleService = salleService;
    }

    public ModifierSalle() {
        // Constructeur par défaut
    }

    public static Salle_de_sport.EnumRegion convertToEnumRegion(String regionString) {

        return Salle_de_sport.EnumRegion.valueOf(regionString); // Cela suppose que les noms des régions sont exactement les mêmes que les noms des EnumRegion
    }




    @FXML
    private void selectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers Image", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            imageField.setText(selectedFile.getAbsolutePath());
            // Charger l'image dans l'ImageView
            javafx.scene.image.Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        }
    }

    @FXML
    private void modifier_salle() {
        try {
            int idSalle = Integer.parseInt(idSalleField.getText());
            String nomSalle = nomSalleField.getText();
            String description = descriptionField.getText();
            String regionString = regionComboBox.getValue();
            String adresse = adresseField.getText();
            String image = imageField.getText();

            if (nomSalle.isEmpty() || description.isEmpty() || regionString == null || regionString.isEmpty() || adresse.isEmpty() || image.isEmpty()) {
                throw new IllegalArgumentException("Tous les champs doivent être remplis.");
            }

            Salle_de_sport.EnumRegion region = Salle_de_sport.convertToEnumRegion(regionString);

            // Chargez les détails de la salle existante à partir du service
            Salle_de_sport salleExistant = salleService.getSalleParID(idSalle);

            // Mettez à jour les détails de la salle existante avec les nouvelles valeurs
            salleExistant.setNom_salle(nomSalle);
            salleExistant.setDescription_salle(description);
            salleExistant.setRegion_salle(region);
            salleExistant.setAdresse_salle(adresse);
            salleExistant.setImage_salle(image);

            // Appelez la méthode modifier_salle de votre SalleService avec la salle mise à jour
            salleService.modifier_salle(salleExistant);

            // Affichez un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Salle modifiée");
            alert.setHeaderText(null);
            alert.setContentText("La salle a été modifiée avec succès !");
            alert.showAndWait();
        } catch (SQLException e) {
            // Gérez l'exception SQLException ici
            e.printStackTrace();
            // Affichez un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors de la modification de la salle");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de la modification de la salle. Veuillez réessayer.");
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            // Gérez l'exception IllegalArgumentException ici
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors de la modification de la salle");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
