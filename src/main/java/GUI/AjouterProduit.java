package GUI;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.CategorieProduit;
import models.Produit;
import services.ProduitService;

public class AjouterProduit {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    private Button ajouterProduitButton;

    // Variable pour stocker l'URL de l'image
    private String imageUrl;

    @FXML
    void initialize() {

        // Ajouter les options au ComboBox categ
        ObservableList<String> options = FXCollections.observableArrayList(
                "Proteines", "Alimentation Sportive", "Vitamines", "PreWorkout", "Accessoires Nutrition"
        );
        categ.setItems(options);

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

        ajouterProduitButton.setOnAction(event -> {
            // Vérifier si tous les champs sont remplis
            if (Ref.getText().isEmpty() || nomP.getText().isEmpty() || descrip.getText().isEmpty() ||
                    prix.getText().isEmpty() || categ.getValue() == null || imageView.getImage() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Vous devez remplir tous les champs.");
                alert.show();
                return; // Sortir de la méthode si un champ est vide
            }

            // Créer un objet Produit avec les valeurs saisies
            Produit nouveauProduit = new Produit();
            nouveauProduit.setReference(Ref.getText());
            nouveauProduit.setNom(nomP.getText());
            nouveauProduit.setDescription(descrip.getText());
            nouveauProduit.setPrix(Double.parseDouble(prix.getText()));
            nouveauProduit.setImage(imageUrl); // Utiliser l'URL de l'image stockée
            nouveauProduit.setCategorie(convertirCategorie(categ.getValue()));

            // Appeler la méthode ajouterPrd de ProduitService
            ProduitService produitService = new ProduitService();
            try {
                produitService.ajouterPrd(nouveauProduit);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Le produit a été ajouté avec succès !");
                alert.show();

                // Si l'ajout réussit, vous pouvez effectuer d'autres actions ici (par exemple, vider les champs de saisie)
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }
        });
    }

    // Méthode pour convertir la chaîne sélectionnée en une instance de CategorieProduit
    private CategorieProduit convertirCategorie(String categorieString) {
        switch (categorieString) {
            case "Proteines":
                return CategorieProduit.Proteines;
            case "Alimentation Sportive":
                return CategorieProduit.AlimentationSportive;
            case "Vitamines":
                return CategorieProduit.Vitamines;
            case "PreWorkout":
                return CategorieProduit.PreWorkout;
            case "Accessoires Nutrition":
                return CategorieProduit.AccessoiresNutrition;
            default:
                return null; // Gérer le cas par défaut selon vos besoins
        }
    }

    public void Logout(ActionEvent actionEvent) {
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

    public void reservation(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReservation.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("reservation");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void produit(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RecupererProduitAdmin.fxml"));
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
