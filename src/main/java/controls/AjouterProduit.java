package controls;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class AjouterProduit {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField Ref;

    @FXML
    private TextField categ;

    @FXML
    private TextField descrip;

    @FXML
    private TextField nomP;

    @FXML
    private TextField prix;

    @FXML
    private Button uploadImg;

    @FXML
    private ImageView imageView;

    @FXML
    void initialize() {
        // Gestionnaire d'événements pour le bouton de téléchargement de l'image
        uploadImg.setOnAction(event -> {
            // Création d'un FileChooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir une image");

            // Filtrer uniquement les fichiers image
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.jpg, *.jpeg, *.png)", "*.jpg", "*.jpeg", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);

            // Afficher la boîte de dialogue de sélection de fichier et obtenir le fichier sélectionné
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                // Charger l'image sélectionnée dans l'ImageView
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
            }
        });
    }
}
