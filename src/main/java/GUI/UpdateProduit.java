package GUI;

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

import java.io.File;
import java.io.IOException;
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
        categ.getItems().addAll("Proteines", "Alimentation Sportive", "Vitamines", "PreWorkout", "Accessoires Nutrition");
        uploadImg.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir une image");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.jpg, *.jpeg, *.png)", "*.jpg", "*.jpeg", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);

            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                // Charger l'image sélectionnée dans l'ImageView
                imageUrl = selectedFile.toURI().toString(); // Stocker l'URL de l'image
                Image image = new Image(imageUrl); // Créer l'objet Image avec l'URL
                imageView.setImage(image);
            }
        });

        modifierProduitButton.setOnAction(event -> {
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
            produit.setCategorie(categ.getValue());


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
        try {
            Image image = new Image(produit.getImage());
            imageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CheckUsers(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPersonne.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Afficher Simple Utilisateur");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void CheckWorkers(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherTravailleur.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Afficher Travailleur");
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
            primaryStage.setTitle("Reservation");
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
