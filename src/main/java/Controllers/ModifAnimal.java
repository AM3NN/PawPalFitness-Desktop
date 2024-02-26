package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import Models.Animal;
import Services.AnimalService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifAnimal {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField IDa;

    @FXML
    private TextField IDc;

    @FXML
    private TextField IDn;

    @FXML
    private TextField IDp;

    @FXML
    private TextField IDt;

    @FXML
    private TextArea IDd;

    @FXML
    private Button SU_Animal;

    @FXML
    private Button ajout;

    @FXML
    private Button supp;

    @FXML
    private Button btn_profile;

    @FXML
    void IntPAnimaux(ActionEvent event)throws IOException {
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/TypeAnimal.fxml")));
        Scene scene = new Scene(root,800,550);
        stage.setScene(scene);
    }

    @FXML
    void Modifier(ActionEvent event){
        /*---------Modifier--------------
        AnimalService animalService = new AnimalService();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutAnimal.fxml"));
        AjoutAnimal ajoutAnimal = loader.getController();
       String a = IDn.setText(ajoutAnimal.IDn.getText());
        IDa.setText(ajoutAnimal.IDa.getText());
        IDp.setText(ajoutAnimal.IDp.getText());
        IDt.setText(ajoutAnimal.IDtype.getText());
        IDd.setText(ajoutAnimal.IDam.getText());

        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        /*ICONE alert*/
       /* alert.setContentText(IDn.getText()+"Veillez saisir les nouvelles cordonnées");
        alert.show();

        animalService.modifierAnimal(IDn.getText(),Integer.parseInt(IDa.getText()),IDc.getText(),IDt.getText(),IDd.getText(), Float.parseFloat(IDp.getText()),a);*/

        //-----------Ajouter nouvelles coordonnées-------------
        Animal animal=new Animal(IDn.getText(), Integer.parseInt(IDa.getText()), IDc.getText(),IDt.getText(),IDd.getText(), Float.parseFloat(IDp.getText()));

        try {
            AnimalService animalService = new AnimalService();
            animalService.ajouterAnimal(animal);
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            /*ICONE alert*/
            alert.setContentText(IDn.getText()+" est modifié avec succées");
            alert.show();
            IDn.clear();
            IDa.clear();
            IDp.clear();
            IDt.clear();
            IDd.clear();
        } catch (SQLException e) {
            System.out.println(e.getMessage());}
    }

    @FXML
    void Ajouter(ActionEvent event)throws IOException {
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AjoutAnimal.fxml")));
        Scene scene = new Scene(root,800,550);
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
