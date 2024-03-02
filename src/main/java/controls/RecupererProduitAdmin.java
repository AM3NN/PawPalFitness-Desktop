package controls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import models.Produit;
import services.ProduitService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RecupererProduitAdmin {

    @FXML
    private VBox productContainer;

    private ProduitService produitService = new ProduitService();

    @FXML
    public void initialize() {
        try {
            // Effacer les produits précédents s'ils existent
            productContainer.getChildren().clear();

            // Récupérer la liste des produits
            List<Produit> produits = produitService.recupererPrd();

            // Créer et ajouter un cadre pour chaque produit
            for (Produit produit : produits) {
                AnchorPane productPane = createProductPane(produit);
                productContainer.getChildren().add(productPane);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors de la récupération des produits : " + e.getMessage());
            alert.show();
        }
    }

    private AnchorPane createProductPane(Produit produit) {
        // Créer un nouvel AnchorPane pour le produit
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10px; -fx-font-weight: bold;");
        anchorPane.setPrefSize(200, 300); // Définir la taille du cadre carré

        // Créer un label pour afficher le nom du produit
        Label nameLabel = new Label(produit.getNom());
        nameLabel.setStyle("-fx-font-weight: bold;");
        anchorPane.getChildren().add(nameLabel);
        AnchorPane.setTopAnchor(nameLabel, 10.0);
        AnchorPane.setLeftAnchor(nameLabel, 10.0);
        AnchorPane.setRightAnchor(nameLabel, 10.0); // Centrer le nom du produit

        // Créer une ImageView pour afficher l'image du produit
        try {
            Image image = new Image(produit.getImage());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(100); // Définir la largeur de l'image
            imageView.setPreserveRatio(true);
            anchorPane.getChildren().add(imageView);
            AnchorPane.setTopAnchor(imageView, 40.0);
            AnchorPane.setLeftAnchor(imageView, 50.0); // Ajuster la position horizontale pour centrer l'image
        } catch (Exception e) {
            e.printStackTrace(); // Gérer l'erreur d'URL d'image invalide
        }

        // Créer un label pour afficher la catégorie du produit
        Label categoryLabel = new Label("Catégorie: " + produit.getCategorie().toString());
        anchorPane.getChildren().add(categoryLabel);
        AnchorPane.setTopAnchor(categoryLabel, 160.0);
        AnchorPane.setLeftAnchor(categoryLabel, 10.0);
        AnchorPane.setRightAnchor(categoryLabel, 10.0); // Centrer la catégorie

        // Créer un label pour afficher la description du produit
        Label descriptionLabel = new Label(produit.getDescription());
        anchorPane.getChildren().add(descriptionLabel);
        AnchorPane.setTopAnchor(descriptionLabel, 190.0);
        AnchorPane.setLeftAnchor(descriptionLabel, 10.0);
        AnchorPane.setRightAnchor(descriptionLabel, 10.0); // Centrer la description

        // Créer un label pour afficher le prix du produit
        Label priceLabel = new Label("Prix: " + produit.getPrix() + " DT");
        anchorPane.getChildren().add(priceLabel);
        AnchorPane.setTopAnchor(priceLabel, 220.0);
        AnchorPane.setLeftAnchor(priceLabel, 10.0);
        AnchorPane.setRightAnchor(priceLabel, 10.0); // Centrer le prix

        // Créer un bouton pour mettre à jour le produit
        Button updateButton = new Button("Mettre à jour le produit");
        updateButton.setStyle("-fx-background-color: orange;");
        updateButton.setOnAction(event -> {
            // Appeler la méthode pour charger l'interface de mise à jour du produit
            loadUpdateProduitScene(produit);
        });
        anchorPane.getChildren().add(updateButton);
        AnchorPane.setTopAnchor(updateButton, 250.0);
        AnchorPane.setLeftAnchor(updateButton, 10.0);
        AnchorPane.setRightAnchor(updateButton, 10.0); // Centrer le bouton de mise à jour


        // Créer un bouton pour supprimer le produit
        Button deleteButton = new Button("Supprimer le produit");
        deleteButton.setStyle("-fx-background-color: red;");
        anchorPane.getChildren().add(deleteButton);
        AnchorPane.setTopAnchor(deleteButton, 280.0);
        AnchorPane.setLeftAnchor(deleteButton, 10.0);
        AnchorPane.setRightAnchor(deleteButton, 10.0); // Centrer le bouton de suppression

        return anchorPane;
    }

    // Méthode pour charger l'interface de mise à jour du produit
    private void loadUpdateProduitScene(Produit produit) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateProduit.fxml"));
            Parent root = loader.load();
            UpdateProduit updateProduit = loader.getController(); // Récupération du contrôleur de la nouvelle scène
            updateProduit.initData(produit); // Initialisation des données du produit à mettre à jour
            Scene currentScene = productContainer.getScene();
            currentScene.setRoot(root); // Remplacement de la scène actuelle par la nouvelle scène
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
