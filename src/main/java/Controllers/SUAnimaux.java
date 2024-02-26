package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import Models.Animal;
import Services.AnimalService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SUAnimaux{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Ajout;

    @FXML
    private Button Modif;

    @FXML
    private Button SU_Animal;

    @FXML
    private Button Supp;

    @FXML
    private Button Supp2;

    @FXML
    private Button btn_profile;

    @FXML
    private TextField nom;

    @FXML
    private TextField age;

    @FXML
    private TextField poids;

    @FXML
    private Button Qu;

    private AnimalService as=new AnimalService();

    @FXML
    void IntPAnimaux(ActionEvent event) throws IOException {
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/TypeAnimal.fxml")));
        Scene scene = new Scene(root,800,550);
        stage.setScene(scene);
    }
    @FXML
    void Ajouter(ActionEvent event)throws IOException {
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AjoutAnimal.fxml")));
        Scene scene = new Scene(root,800,550);
        stage.setScene(scene);
    }

    @FXML
    void Supprimer(ActionEvent event)throws IOException {
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/SupprimerAnimal.fxml")));
        Scene scene = new Scene(root,800,550);
        stage.setScene(scene);
    }

    @FXML
    void Modifier(ActionEvent event)throws IOException {
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ModifAnimal.fxml")));
        Scene scene = new Scene(root,800,550);
        stage.setScene(scene);
    }


    @FXML
    void initialize() throws IOException,SQLException{
        List<Animal> ax = as.animalparCategorie();
           if (!ax.isEmpty()) {
                Animal a1=ax.get(0);
                nom.setText(a1.getNom());
                age.setText(String.valueOf(Integer.parseInt(String.valueOf(a1.getAge()))));
                poids.setText(String.valueOf(Float.parseFloat(String.valueOf(a1.getPoids())))+" Kg");
            }
    }

    @FXML
    void Quitter(ActionEvent event)throws IOException {
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/TypeAnimal.fxml")));
        Scene scene = new Scene(root,800,550);
        stage.setScene(scene);
    }

}

