package controls;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.CategorieProduit;
import models.Produit;
import services.ProduitService;

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

    @FXML
    void initialize() {
        // Initialise les valeurs du ComboBox
        categ.getItems().addAll("Proteines", "Alimentation Sportive", "Vitamines", "PreWorkout", "Accessoires Nutrition");

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

            // Crée un objet Produit avec les valeurs saisies
            Produit produitModifie = new Produit();
            produitModifie.setReference(Ref.getText());
            produitModifie.setNom(nomP.getText());
            produitModifie.setDescription(descrip.getText());
            produitModifie.setPrix(Double.parseDouble(prix.getText()));
            produitModifie.setImage(String.valueOf(imageView.getImage()));
            produitModifie.setCategorie(CategorieProduit.valueOf(categ.getValue()));

            // Appelle la méthode modifierPrd de ProduitService
            ProduitService produitService = new ProduitService();
            try {
                produitService.modifierPrd(produitModifie);
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
