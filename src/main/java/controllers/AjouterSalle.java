package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AjouterSalle {

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
    public void ajouterSalle() {
        // Récupérer les valeurs saisies dans les champs
        String nomSalle = nomSalleField.getText();
        String description = descriptionField.getText();
        String region = regionComboBox.getValue();
        String image = imageField.getText();
        String adresse = adresseField.getText();

        // Vous pouvez maintenant utiliser ces valeurs pour ajouter une salle de sport
        // Appelez votre méthode d'ajout de salle avec ces valeurs
        // par exemple :
        // salleService.ajouterSalle(new Salle_de_sport(nomSalle, description, region, image, adresse));
    }

    @FXML
    public void AjouterSalle(ActionEvent actionEvent) {
        // Méthode pour le gestionnaire d'événements AjouterSalle
    }
}
