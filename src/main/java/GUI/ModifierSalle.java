package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Salle_de_sport;
import services.SalleService;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class ModifierSalle {

    @FXML
    private ImageView imageView;
    @FXML
    private TextField nomSalleField;
    @FXML
    private TextField descriptionField;
    @FXML
    private ComboBox<Salle_de_sport.EnumRegion> regionComboBox;
    @FXML
    private TextField adresseField;
    @FXML
    private TextField imageField;

    private Salle_de_sport salleDeSport;

    public void initialize() {
        // Ajouter chaque valeur de l'énumération EnumRegion au ComboBox
        for (Salle_de_sport.EnumRegion region : Salle_de_sport.EnumRegion.values()) {
            regionComboBox.getItems().add(Salle_de_sport.EnumRegion.valueOf(region.toString()));
        }
    }

    @FXML
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

    public void initData(Salle_de_sport salleDeSport) {
        this.salleDeSport = salleDeSport;

        // Charger les données de la salle sélectionnée dans les champs de texte et de ComboBox
        nomSalleField.setText(salleDeSport.getNom_salle());
        descriptionField.setText(salleDeSport.getDescription_salle());
        regionComboBox.setValue(salleDeSport.getRegion_salle());
        adresseField.setText(salleDeSport.getAdresse_salle());
        imageField.setText(salleDeSport.getImage_salle());
    }

    @FXML
    private void modifierSalle(ActionEvent event) {
        try {
            // Mettre à jour les données de la salle avec les valeurs des champs de texte et de ComboBox
            salleDeSport.setNom_salle(nomSalleField.getText());
            salleDeSport.setDescription_salle(descriptionField.getText());
            salleDeSport.setRegion_salle(regionComboBox.getValue());
            salleDeSport.setAdresse_salle(adresseField.getText());
            salleDeSport.setImage_salle(imageField.getText());

            String nomSalle = nomSalleField.getText();
            String description = descriptionField.getText();
            String image = imageField.getText();
            String adresse = adresseField.getText();
            String regionString = regionComboBox.getValue().toString();


            if (regionString == null || regionString.isEmpty()) {
                throw new IllegalArgumentException("Veuillez sélectionner une région.");
            }

            if (nomSalle.isEmpty() || description.isEmpty() || image.isEmpty() || adresse.isEmpty()) {
                throw new IllegalArgumentException("Tous les champs doivent être remplis.");
            }

            // Appeler la méthode de service pour modifier la salle dans la base de données
            SalleService salleService = new SalleService();
            salleService.modifier_salle(salleDeSport);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Salle modifiée");
            alert.setHeaderText(null);
            alert.setContentText("La salle a été modifiée avec succès !");
            alert.showAndWait();
            retour(event);
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors de l'ajout de la salle");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de l'ajout de la salle. Veuillez réessayer!");
            alert.showAndWait();
        }
    }

    @FXML
    private void annulerModif(ActionEvent event) {
        retour(event);
    }

    private void retour(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSallesAdmin.fxml"));
            Parent ModifSallesRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(ModifSallesRoot));
            primaryStage.setTitle("");
            primaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}

/*
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

            Salle_de_sport.EnumRegion region = Salle_de_sport.EnumRegion.valueOf(regionString);

            Salle_de_sport salle = new Salle_de_sport(idSalle, nomSalle, description, region, adresse, image);
            salleService.modifier_salle(salle);

            // Afficher un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Salle modifiée");
            alert.setHeaderText(null);
            alert.setContentText("La salle a été modifiée avec succès !");
            alert.showAndWait();
        } catch (SQLException e) {
            // Gérer l'exception SQLException ici
            e.printStackTrace();
            // Afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors de la modification de la salle");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de la modification de la salle. Veuillez réessayer.");
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            // Gérer l'exception IllegalArgumentException ici
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors de la modification de la salle");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    } */

