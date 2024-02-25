package GUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Personne;
import services.PersonneService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AfficherPersonne {
    @FXML
    private TableView<Personne> table_id;

    @FXML
    private TableColumn<Personne, Integer> idColumn;

    @FXML
    private TableColumn<Personne, String> nomColumn;

    @FXML
    private TableColumn<Personne, String> prenomColumn;

    @FXML
    private TableColumn<Personne, Integer> ageColumn;

    @FXML
    private TableColumn<Personne, String> regionColumn;

    @FXML
    private TableColumn<Personne, String> adresseColumn;

    @FXML
    private TableColumn<Personne, String> emailColumn;

    @FXML
    private TableColumn<Personne, String> passwordColumn;

    @FXML
    private TextField nom_modif;

    @FXML
    private TextField prenom_modif;

    @FXML
    private TextField age_modif;

    @FXML
    private TextField region_modif;

    @FXML
    private TextField email_modif;

    @FXML
    private TextField password_modif;



    @FXML
    private Button btn_supprimer;

    private PersonneService personneService = new PersonneService();

    @FXML
    public void initialize() {
        try {
            idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
            nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
            prenomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
            ageColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAge()).asObject());
            regionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRegion()));
            emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
            passwordColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
            List<Personne> personnes = personneService.recuperer();
            table_id.getItems().setAll(personnes);
            table_id.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    nom_modif.setText(newValue.getNom());
                    prenom_modif.setText(newValue.getPrenom());
                    age_modif.setText(Integer.toString(newValue.getAge()));
                    region_modif.setText(newValue.getRegion());
                    email_modif.setText(newValue.getEmail());
                    password_modif.setText(newValue.getPassword());
                }
            });
            btn_supprimer.setOnAction(event -> {
                Personne selectedPersonne = table_id.getSelectionModel().getSelectedItem();
                if (selectedPersonne != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Supprimer la personne");
                    alert.setContentText("Voulez-vous vraiment supprimer la personne ?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        try {
                            personneService.supprimer(selectedPersonne.getId());
                            table_id.getItems().remove(selectedPersonne);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Home(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Back");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
