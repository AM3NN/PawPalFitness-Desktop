package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.CategorieProduit;
import models.Commande;
import models.Produit;
import services.CommandeService;
import services.ProduitService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AfficherProduitUser {

    @FXML
    private VBox productContainer;

    @FXML
    private VBox categoryContainer;

    @FXML
    private Button monPanier;

    private ProduitService produitService = new ProduitService();
    private CommandeService commandeService = new CommandeService();
    private int userId;

    @FXML
    public void initialize() {
        loadProducts();
        loadCategories();
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }


    private void loadProducts() {
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

    private void loadCategories() {
        List<CategorieProduit> categories = new ArrayList<>();
        categories.add(CategorieProduit.Proteines);
        categories.add(CategorieProduit.AlimentationSportive);
        categories.add(CategorieProduit.Vitamines);
        categories.add(CategorieProduit.PreWorkout);
        categories.add(CategorieProduit.AccessoiresNutrition);

        for (CategorieProduit categorie : categories) {
            CheckBox checkBox = new CheckBox(categorie.toString());
            checkBox.setOnAction(event -> {
                try {
                    if (checkBox.isSelected()) {
                        filterProductsByCategory(categorie);
                    } else {
                        loadProducts();
                    }
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erreur lors du filtrage des produits par catégorie : " + e.getMessage());
                    alert.show();
                }
            });
            categoryContainer.getChildren().add(checkBox);
        }
    }

    private void filterProductsByCategory(CategorieProduit categorie) throws SQLException {
        productContainer.getChildren().clear();
        List<Produit> produits = produitService.filtrerParCategorie(categorie);
        for (Produit produit : produits) {
            AnchorPane productPane = createProductPane(produit);
            productContainer.getChildren().add(productPane);
        }
    }

    private AnchorPane createProductPane(Produit produit) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10px; -fx-font-weight: bold;");
        anchorPane.setPrefSize(200, 300);
        Label nameLabel = new Label(produit.getNom());
        nameLabel.setStyle("-fx-font-weight: bold;");
        anchorPane.getChildren().add(nameLabel);
        AnchorPane.setTopAnchor(nameLabel, 10.0);
        AnchorPane.setLeftAnchor(nameLabel, 10.0);
        AnchorPane.setRightAnchor(nameLabel, 10.0);

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

        // Créer un bouton pour ajouter au panier
        Button addToCartButton = new Button("Ajouter au panier");
        addToCartButton.setStyle("-fx-background-color: green;");
        addToCartButton.setOnAction(event -> {
            Produit produitSelected = produit;
            Commande commande = new Commande();
            commandeService.ajouterProduitAuPanier(commande, produitSelected);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Ce produit est ajouté au panier");
            alert.show();
        });
        anchorPane.getChildren().add(addToCartButton);
        AnchorPane.setTopAnchor(addToCartButton, 250.0);
        AnchorPane.setLeftAnchor(addToCartButton, 10.0);
        AnchorPane.setRightAnchor(addToCartButton, 10.0); // Centrer le bouton "Ajouter au panier"

        return anchorPane;
    }

    @FXML
    private void monPanierClicked(ActionEvent event) {
        try {
            // Charger le fichier FXML de la vue du panier
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Panier.fxml"));
            Parent root = loader.load();

            // Créer un nouveau contrôleur
            Panier panierController = loader.getController();

            // Passer à la nouvelle scène contenant la vue du panier
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.getMessage();
            // Gérer les erreurs lors du chargement de la vue du panier
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors du chargement du panier : " + e.getMessage());
            alert.show();
        }
    }

    public void Logout(ActionEvent actionEvent) {
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

    public void CheckProfile(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
            Parent profileRoot = loader.load();
            Profile shoppingPageController = loader.getController();
            shoppingPageController.setUserId(userId);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(profileRoot));
            primaryStage.setTitle("Home");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void IntPAnimaux(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TypeAnimal.fxml"));
            Parent profileRoot = loader.load();
            TypeAnimal shoppingPageController = loader.getController();
            shoppingPageController.setUserId(userId);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(profileRoot));
            primaryStage.setTitle("Home");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
