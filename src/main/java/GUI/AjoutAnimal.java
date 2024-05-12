package GUI;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

import models.Animal;
import models.Categorie;
import services.AnimalService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import utils.MyDabase;

public class AjoutAnimal {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField IDa;

    @FXML
    private RadioButton Rchat;

    @FXML
    private RadioButton Rchien;

    @FXML
    private TextField IDn;

    @FXML
    private TextField IDp;

    @FXML
    private TextArea IDam;

    @FXML
    private TextField IDtype;

    @FXML
    private Button SU_Animal;

    @FXML
    private Button btn_profile;

    @FXML
    private Button Qu;


    public String btnradio(String Rch1, String Rch2) {
        if (Rchat.isSelected()) {
            Rch1 = Rchat.getText();
            Rch2 = null;
        } else Rch1 = Rchien.getText();
        if (Rch1 == null)
            return Rch2;
        return Rch1;
    }

    @FXML
    void Ajouter(ActionEvent event) {

        String nom = IDn.getText();
        String age = IDa.getText();
        String categorie = btnradio(Rchat.getText(), Rchien.getText());
        Categorie c = new Categorie(categorie);
        AnimalService animalService = new AnimalService();
        int idc = animalService.fetchCategoryId(categorie);
        if (idc == -1) {
            // Category doesn't exist, handle the error
            System.out.println("Error: Category does not exist in the database.");
            return; // Exit the method
        }
        String type = IDtype.getText();
        String details = IDam.getText();
        String poids = IDp.getText();

        // *************controle de saisie
        if (nom.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            /*ICONE alert a faire*/
            alert.setContentText("Veillez remplir le nom de votre animal");
            alert.show();
        } else if (!nom.matches("[a-zA-Z]+")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            /*ICONE alert a faire*/
            alert.setContentText("Veillez remplir le nom de votre animal Correctement");
            alert.show();
        } else if (age.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Type Non validé !");
            /*ICONE alert a faire*/
            alert.setContentText("Veillez remplir l'age de votre animal");
            alert.show();
        } else if (!age.matches("[0-9]*")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Type Non validé !");
            alert.setContentText("Veuillez entrer un age de type entier validé !");
            alert.showAndWait();
        } else if (categorie.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            /*ICONE alert a faire*/
            alert.setContentText("Veillez choisir la categorie de votre animal");
            alert.show();
        } else if (type.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            /*ICONE alert a faire*/
            alert.setContentText("Veillez remplir le type de votre animal");
            alert.show();
        } else if (!type.matches("[a-zA-Z]+")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            /*ICONE alert a faire*/
            alert.setContentText("Veillez remplir le Type de votre animal Correctement");
            alert.show();
        } else if (details.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            /*ICONE alert a faire*/
            alert.setContentText("Veillez remplir quelque details de votre animal");
            alert.show();
        }
        //*******************

        Animal animal = new Animal(idc,50, Integer.parseInt(IDa.getText()),nom,type, details, Float.parseFloat(IDp.getText()));
        try {
            animalService.ajouterAnimal(animal);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            /*ICONE alert*/
            alert.setContentText(IDn.getText() + " est ajouté avec succées");
            alert.show();
            IDn.clear();
            IDa.clear();
            IDp.clear();
            IDtype.clear();
            IDam.clear();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void IntPAnimaux(ActionEvent event) throws IOException {
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/TypeAnimal.fxml")));
        Scene scene = new Scene(root, 800, 550);
        stage.setScene(scene);
    }

    @FXML
    void Quitter(ActionEvent event) throws IOException {
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/TypeAnimal.fxml")));
        Scene scene = new Scene(root, 800, 550);
        stage.setScene(scene);
    }
    @FXML
    void Supprimer(ActionEvent event) throws IOException {
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/SupprimerAnimal.fxml")));
        Scene scene = new Scene(root,800,550);
        stage.setScene(scene);
    }

    @FXML
    void initialize() {
    }
}



