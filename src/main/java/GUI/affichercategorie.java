package GUI;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Animal;
import models.Categorie;
import services.AnimalService;
import services.CategorieService;

public class affichercategorie {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_animaux;

    @FXML
    private Hyperlink btn_home;

    @FXML
    private Button btn_produit;

    @FXML
    private Button btn_produit1;

    @FXML
    private Button btn_reservations;

    @FXML
    private Button btn_users;

    @FXML
    private Button btn_workers;

    @FXML
    private TableColumn<Categorie, String> nom;

    @FXML
    private Button supp;

    @FXML
    private TableView<Categorie> table;

    @FXML
    void Abonnements(ActionEvent event) {

    }

    @FXML
    void CheckUsers(ActionEvent event) {

    }

    @FXML
    void CheckWorkers(ActionEvent event) {

    }

    @FXML
    void Logout(ActionEvent event) {

    }

    @FXML
    void Supprimer(ActionEvent event) throws SQLException {
        Categorie selectedCategorie = table.getSelectionModel().getSelectedItem();
        if (selectedCategorie != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Supprimer Categorie");
            alert.setContentText("Voulez vous vraiment supprimer cette categorie");
            Optional<ButtonType> resultat = alert.showAndWait();
            if (resultat.isPresent()&& resultat.get()==ButtonType.OK){
                CategorieService cs = new CategorieService();
                cs.supprimerCategorie(selectedCategorie.getNomc());
                table.getItems().remove(selectedCategorie);
            }
        }
    }

    @FXML
    void animaux(ActionEvent event) {

    }

    @FXML
    void produit(ActionEvent event) {

    }

    @FXML
    void reservation(ActionEvent event) {

    }

    private void setCellValue(){
        nom.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getNomc()));
    }

    @FXML
    void initialize() {
        setCellValue();
        CategorieService cs = new CategorieService();
        List<Categorie> categ = null;
        try {
            categ = cs.afficherCategorie();
            table.getItems().setAll(categ);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

