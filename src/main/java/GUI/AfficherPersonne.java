package GUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import models.Personne;
import services.PersonneService;

import java.sql.SQLException;
import java.util.List;

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
    private Button btn_modif;

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

            btn_modif.setOnAction(event -> {
                Personne selectedPersonne = table_id.getSelectionModel().getSelectedItem();
                if (selectedPersonne != null) {
                    selectedPersonne.setNom(nom_modif.getText());
                    selectedPersonne.setPrenom(prenom_modif.getText());
                    selectedPersonne.setAge(Integer.parseInt(age_modif.getText()));
                    selectedPersonne.setRegion(region_modif.getText());
                    selectedPersonne.setEmail(email_modif.getText());
                    selectedPersonne.setPassword(password_modif.getText());
                    try {
                        personneService.modifier(selectedPersonne);
                        table_id.refresh();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            });

            btn_supprimer.setOnAction(event -> {
                Personne selectedPersonne = table_id.getSelectionModel().getSelectedItem();
                if (selectedPersonne != null) {
                    try {
                        personneService.supprimer(selectedPersonne.getId());
                        table_id.getItems().remove(selectedPersonne);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
