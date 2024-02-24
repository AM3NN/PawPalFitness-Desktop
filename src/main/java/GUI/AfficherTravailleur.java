package GUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import models.Travailleur;
import services.TravailleurService;

import java.sql.SQLException;
import java.util.List;

public class AfficherTravailleur {
    @FXML
    private TableView<Travailleur> table_id;

    @FXML
    private TableColumn<Travailleur, Integer> id_t;

    @FXML
    private TableColumn<Travailleur, String> nom_t;

    @FXML
    private TableColumn<Travailleur, String> prenom_t;

    @FXML
    private TableColumn<Travailleur, Integer> age_t;

    @FXML
    private TableColumn<Travailleur, String> region_t;

    @FXML
    private TableColumn<Travailleur, String> email_t;

    @FXML
    private TableColumn<Travailleur, String> password_t;

    @FXML
    private TableColumn<Travailleur, Integer> roleId_t;

    @FXML
    private TableColumn<Travailleur, String> diplome_t;

    @FXML
    private TableColumn<Travailleur, String> categorie_t;

    @FXML
    private TableColumn<Travailleur, String> langue_t;

    @FXML
    private TableColumn<Travailleur, String> experience_t;

    @FXML
    private TextField nomt_modif;

    @FXML
    private TextField prenomt_modif;

    @FXML
    private TextField aget_modif;

    @FXML
    private TextField regiont_modif;

    @FXML
    private TextField emailt_modif;

    @FXML
    private TextField passwordt_modif;

    @FXML
    private TextField roleIdt_modif;

    @FXML
    private TextField diplomet_modif;

    @FXML
    private TextField categoriet_modif;

    @FXML
    private TextField languet_modif;

    @FXML
    private TextField experiencet_modif;

    @FXML
    private Button btn_modif;

    @FXML
    private Button btn_supprimer;

    private TravailleurService travailleurService = new TravailleurService();

    @FXML
    public void initialize() {
        try {
            id_t.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
            nom_t.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
            prenom_t.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
            age_t.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAge()).asObject());
            region_t.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRegion()));
            email_t.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
            password_t.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
            roleId_t.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRoleId()).asObject());
            diplome_t.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDiplome()));
            categorie_t.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategorie()));
            langue_t.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLangue()));
            experience_t.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExperience()));

            List<Travailleur> travailleurs = travailleurService.recuperer();
            table_id.getItems().setAll(travailleurs);
            table_id.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    nomt_modif.setText(newValue.getNom());
                    prenomt_modif.setText(newValue.getPrenom());
                    aget_modif.setText(Integer.toString(newValue.getAge()));
                    regiont_modif.setText(newValue.getRegion());
                    emailt_modif.setText(newValue.getEmail());
                    passwordt_modif.setText(newValue.getPassword());
                    roleIdt_modif.setText(Integer.toString(newValue.getRoleId()));
                    diplomet_modif.setText(newValue.getDiplome());
                    categoriet_modif.setText(newValue.getCategorie());
                    languet_modif.setText(newValue.getLangue());
                    experiencet_modif.setText(newValue.getExperience());
                }
            });

            btn_modif.setOnAction(event -> {
                Travailleur selectedTravailleur = table_id.getSelectionModel().getSelectedItem();
                if (selectedTravailleur != null) {
                    selectedTravailleur.setNom(nomt_modif.getText());
                    selectedTravailleur.setPrenom(prenomt_modif.getText());
                    selectedTravailleur.setAge(Integer.parseInt(aget_modif.getText()));
                    selectedTravailleur.setRegion(regiont_modif.getText());
                    selectedTravailleur.setEmail(emailt_modif.getText());
                    selectedTravailleur.setPassword(passwordt_modif.getText());
                    selectedTravailleur.setRoleId(Integer.parseInt(roleIdt_modif.getText()));
                    selectedTravailleur.setDiplome(diplomet_modif.getText());
                    selectedTravailleur.setCategorie(categoriet_modif.getText());
                    selectedTravailleur.setLangue(languet_modif.getText());
                    selectedTravailleur.setExperience(experiencet_modif.getText());
                    try {
                        travailleurService.modifier(selectedTravailleur);
                        table_id.refresh();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            });

            btn_supprimer.setOnAction(event -> {
                Travailleur selectedTravailleur = table_id.getSelectionModel().getSelectedItem();
                if (selectedTravailleur != null) {
                    try {
                        travailleurService.supprimer(selectedTravailleur.getId());
                        table_id.getItems().remove(selectedTravailleur);
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
