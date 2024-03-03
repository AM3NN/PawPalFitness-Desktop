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
import models.Travailleur;
import services.TravailleurService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
    private Button btn_supprimer;

    private TravailleurService travailleurService = new TravailleurService();

    @FXML
    public void initialize() {
        try {
            setCellValueFactories();
            List<Travailleur> travailleurs = travailleurService.recuperer();
            table_id.getItems().setAll(travailleurs);
            table_id.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    System.out.println("Selected Travailleur ID: " + newValue.getId()); // Debug line to print selected Travailleur's ID
                    // You don't need to set the ID value to id_t column
                    // id_t.setText(Integer.toString(newValue.getId())); // Remove this line

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

            btn_supprimer.setOnAction(event -> {
                Travailleur selectedTravailleur = table_id.getSelectionModel().getSelectedItem();
                if (selectedTravailleur != null) {
                    System.out.println("Selected Travailleur ID: " + selectedTravailleur.getId()); // Debug line to print selected Travailleur's ID
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Supprimer le travailleur");
                    alert.setContentText("Voulez-vous vraiment supprimer le travailleur ?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        try {
                            travailleurService.supprimer(selectedTravailleur.getId());
                            System.out.println("Travailleur with ID " + selectedTravailleur.getId() + " has been deleted."); // Debug line to confirm deletion
                            table_id.getItems().remove(selectedTravailleur);
                            System.out.println("Travailleur removed from TableView."); // Debug line to confirm removal from TableView
                        } catch (SQLException e) {
                            e.printStackTrace();
                            System.err.println("Error occurred while deleting Travailleur: " + e.getMessage()); // Debug line to print error message
                        }
                    } else {
                        System.out.println("Deletion cancelled by user."); // Debug line to indicate cancellation
                        System.out.println("Deletion cancelled by user.");
                    }
                } else {
                    System.out.println("No Travailleur selected for deletion."); // Debug line to indicate no selection
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void setCellValueFactories() {
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
