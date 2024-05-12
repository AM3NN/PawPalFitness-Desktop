package GUI;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import models.Animal;
import services.AnimalService;
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
    private TextField oldN;

    @FXML
    private Button SU_Animal;

    @FXML
    private Button ajout;

    @FXML
    private Button supp;

    @FXML
    private Button btn_profile;

    private AnimalService as=new AnimalService();

    @FXML
    void IntPAnimaux(ActionEvent event)throws IOException {
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/TypeAnimal.fxml")));
        Scene scene = new Scene(root,800,550);
        stage.setScene(scene);
    }

    @FXML
    void Modifier(ActionEvent event)throws IOException,SQLException{
        List<Animal> ax = as.animalParNom(oldN.getText());
        Animal a = new Animal(Integer.parseInt(IDa.getText()),IDd.getText(),Float.parseFloat(IDp.getText()));
        if (!ax.isEmpty()){
            Animal animal= ax.get(0);
            as.modifierAnimal(a,oldN.getText());
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            /*ICONE alert*/
            alert.setContentText("Les nouvelles informations de "+oldN.getText()+" sont modifiés avec succées");
            alert.show();
            IDa.clear();
            IDp.clear();
            IDd.clear();
        }
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
        oldN.setPromptText("Donner le nom de votre animal");
        IDa.setPromptText("age");
        IDp.setPromptText("poids");
        IDd.setPromptText("Nouveaux details");
    }

    public void supprimer(ActionEvent event) {
    }
}
