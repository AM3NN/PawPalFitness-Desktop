package controls;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import models.CategorieProduit;
import models.Produit;
import services.ProduitService;

import java.io.File;
import java.sql.SQLException;

public class UpdateProduit {

    @FXML
    private TextField Ref;

    @FXML
    private ComboBox<String> categ;

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
    private Button modifierProduitButton;

    private Produit produit;
    private String imageUrl;

    private ProduitService produitService = new ProduitService();

    @FXML
    void initialize() {
        // Initialise les valeurs du ComboBox
        categ.getItems().addAll("Proteines", "Alimentation Sportive", "Vitamines", "PreWorkout", "Accessoires Nutrition");

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
                imageUrl = selectedFile.toURI().toString(); // Stocker l'URL de l'image
                Image image = new Image(imageUrl); // Créer l'objet Image avec l'URL
                imageView.setImage(image);
            }
        });

        // Gestionnaire d'événements pour le bouton de modification de produit
        modifierProduitButton.setOnAction(event -> {
            // Vérifie si tous les champs sont remplis
            if (Ref.getText().isEmpty() || nomP.getText().isEmpty() || descrip.getText().isEmpty() ||
                    prix.getText().isEmpty() || categ.getValue() == null || imageView.getImage() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Vous devez remplir tous les champs.");
                alert.show();
                return; // Sortir de la méthode si un champ est vide
            }

            // Met à jour les valeurs du produit
            produit.setNom(nomP.getText());
            produit.setDescription(descrip.getText());
            produit.setPrix(Double.parseDouble(prix.getText()));
            produit.setImage(imageUrl);
            produit.setCategorie(CategorieProduit.valueOf(categ.getValue()));

            // Appelle la méthode modifierPrd de ProduitService
            try {
                produitService.modifierPrd(produit, produit.getReference());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Le produit a été modifié avec succès !");
                alert.show();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }
        });
    }

    public void initData(Produit produit) {
        this.produit = produit;
        Ref.setText(produit.getReference());
        nomP.setText(produit.getNom());
        descrip.setText(produit.getDescription());
        prix.setText(String.valueOf(produit.getPrix()));
        categ.setValue(produit.getCategorie().toString());

        // Charger et afficher l'image
        try {
            Image image = new Image(produit.getImage());
            imageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
