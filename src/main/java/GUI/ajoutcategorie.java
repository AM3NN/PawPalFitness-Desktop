package GUI;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import models.Categorie;
import services.CategorieService;

public class ajoutcategorie {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField IDn;

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
    void Abonnements(ActionEvent event) {

    }

    @FXML
    void Ajouter(ActionEvent event) {
        String nom = IDn.getText();
        if (nom.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            /*ICONE alert a faire*/
            alert.setContentText("Veillez remplir le nom de la catégorie");
            alert.show();
        } else if (!nom.matches("[a-zA-Z]+")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            /*ICONE alert a faire*/
            alert.setContentText("Veillez remplir le nom Correctement");
            alert.show();}
        else {


        CategorieService categorieService = new CategorieService();
        Categorie categorie= new Categorie(nom);

        try {
            categorieService.ajouterCategorie(categorie);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            /*ICONE alert*/
            alert.setContentText(IDn.getText() + " est ajouté avec succées");
            alert.show();
            IDn.clear();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        }
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
    void affichera(ActionEvent event) {

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

    @FXML
    void initialize() {


    }

}
