package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.CategorieProduit;
import models.Produit;
import services.ProduitService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecupererProduitAdmin {

    @FXML
    private VBox productContainer;

    @FXML
    private VBox categoryContainer;

    private ProduitService produitService = new ProduitService();
    private int userId;

    @FXML
    public void initialize() {
        loadProducts();
        loadCategories();
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
        // Effacer les produits actuels
        productContainer.getChildren().clear();

        // Récupérer les produits de la catégorie spécifiée
        List<Produit> produits = produitService.filtrerParCategorie(categorie);

        // Créer et ajouter un cadre pour chaque produit
        for (Produit produit : produits) {
            AnchorPane productPane = createProductPane(produit);
            productContainer.getChildren().add(productPane);
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
        deleteButton.setOnAction(event -> {
            try {
                produitService.supprimerPrd(produit.getIdP());
                loadProducts(); // Recharger la liste des produits après la suppression
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Le produit a été supprimé avec succès !");
                alert.show();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erreur lors de la suppression du produit : " + e.getMessage());
                alert.show();
            }
        });
        anchorPane.getChildren().add(deleteButton);
        AnchorPane.setTopAnchor(deleteButton, 280.0);
        AnchorPane.setLeftAnchor(deleteButton, 10.0);
        AnchorPane.setRightAnchor(deleteButton, 10.0); // Centrer le bouton de suppression

        return anchorPane;
    }private void loadUpdateProduitScene(Produit produit) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateProduit.fxml"));
            Parent root = loader.load();
            UpdateProduit updateProduit = loader.getController(); // Retrieve the controller of the new scene
            updateProduit.initData(produit); // Initialize the data of the product to be updated
            Scene newScene = new Scene(root, 800, 550);
            Stage primaryStage = (Stage) productContainer.getScene().getWindow();
            primaryStage.setScene(newScene); // Replace the current scene with the new scene
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void Logout(ActionEvent actionEvent) {
    }

    public void reservation(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReservation.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Reservation");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CheckWorkers(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherTravailleur.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Travailleur");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CheckUsers(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherPersonne.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("personne");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setUserId(int userId) {
        this.userId = userId;
        
    }
    public void produit(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduitUser.fxml"));
            Parent profileRoot = loader.load();
            AfficherProduitUser shoppingPageController = loader.getController();
            shoppingPageController.setUserId(userId);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(profileRoot));
            primaryStage.setTitle("Home");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addProduit(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduit.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("produit");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
