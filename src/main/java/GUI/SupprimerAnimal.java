package GUI;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import models.Animal;
import services.AnimalService;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SupprimerAnimal {

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
    private Button btn_profile;

    @FXML
    private Button supp;

    @FXML
    private TableView<Animal> table;

    @FXML
    private TableColumn<Animal, Integer> age;

    @FXML
    private TableColumn<Animal, String> nom;

    @FXML
    private TableColumn<Animal, String> poids;

    @FXML
    private TableColumn<Animal, String> type;

    @FXML
    private TableColumn<Animal, String> details;

    @FXML
    void IntPAnimaux(ActionEvent event)throws IOException {
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
    void Modifier(ActionEvent event)throws IOException {
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ModifAnimal.fxml")));
        Scene scene = new Scene(root,800,550);
        stage.setScene(scene);
    }


    @FXML
    void Supprimer(ActionEvent event)throws SQLException {
        Animal selectedAnimal = table.getSelectionModel().getSelectedItem();
        if (selectedAnimal != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Supprimer Animal");
            alert.setContentText("Voulez vous vraiment supprimer votre animal");
            Optional<ButtonType> resultat = alert.showAndWait();
            if (resultat.isPresent()&& resultat.get()==ButtonType.OK){
                AnimalService as = new AnimalService();
                as.supprimerAnimal(selectedAnimal.getNom());
                table.getItems().remove(selectedAnimal);
            }
        }
    }

    private void setCellValue(){
        nom.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getNom()));
        age.setCellValueFactory(cellData->new SimpleIntegerProperty(cellData.getValue().getAge()).asObject());
        type.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getType()));
        details.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getDetails()));
        poids.setCellValueFactory(cellData -> {
            float p = cellData.getValue().getPoids();
            return new SimpleFloatProperty(p).asObject().asString();
        });
    }

    @FXML
    void Quitter(ActionEvent event)throws IOException {
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/TypeAnimal.fxml")));
        Scene scene = new Scene(root,800,550);
        stage.setScene(scene);
    }

    @FXML
    void initialize() {
        setCellValue();
        AnimalService as = new AnimalService();
        List<Animal> animaux = null;
        try {
            animaux = as.afficherAnimal();
            table.getItems().setAll(animaux);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
