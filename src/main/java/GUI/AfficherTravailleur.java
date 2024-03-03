package GUI;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.Travailleur;
import services.TravailleurService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
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
    private TextField searchField;

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
            ObservableList<Travailleur> observableTravailleurs = FXCollections.observableArrayList(travailleurs);
            table_id.setItems(observableTravailleurs);

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

            btn_supprimer.setOnAction(event -> {
                Travailleur selectedTravailleur = table_id.getSelectionModel().getSelectedItem();
                if (selectedTravailleur != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Supprimer le travailleur");
                    alert.setContentText("Voulez-vous vraiment supprimer le travailleur ?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        try {
                            travailleurService.supprimer(selectedTravailleur.getId());
                            observableTravailleurs.remove(selectedTravailleur);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    filterTable(newValue);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void filterTable(String searchText) throws SQLException {
        ObservableList<Travailleur> travailleurs = FXCollections.observableArrayList(travailleurService.recuperer());
        FilteredList<Travailleur> filteredData = new FilteredList<>(travailleurs, p -> true);

        if (searchText == null || searchText.trim().isEmpty()) {
            table_id.setItems(travailleurs);
        } else {
            filteredData.setPredicate(travailleur -> travailleurContainsSearchText(travailleur, searchText));
            table_id.setItems(filteredData);
        }
    }

    private boolean travailleurContainsSearchText(Travailleur travailleur, String searchText) {
        return travailleur.getNom().toLowerCase().contains(searchText.toLowerCase()) ||
                travailleur.getPrenom().toLowerCase().contains(searchText.toLowerCase()) ||
                String.valueOf(travailleur.getAge()).toLowerCase().contains(searchText.toLowerCase()) ||
                travailleur.getRegion().toLowerCase().contains(searchText.toLowerCase()) ||
                travailleur.getEmail().toLowerCase().contains(searchText.toLowerCase()) ||
                travailleur.getPassword().toLowerCase().contains(searchText.toLowerCase()) ||
                String.valueOf(travailleur.getRoleId()).toLowerCase().contains(searchText.toLowerCase()) ||
                travailleur.getDiplome().toLowerCase().contains(searchText.toLowerCase()) ||
                travailleur.getCategorie().toLowerCase().contains(searchText.toLowerCase()) ||
                travailleur.getLangue().toLowerCase().contains(searchText.toLowerCase()) ||
                travailleur.getExperience().toLowerCase().contains(searchText.toLowerCase());
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

    public void handleSearch(KeyEvent keyEvent) {
        String searchText = searchField.getText();
        try {
            filterTable(searchText);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void triByAge(ActionEvent actionEvent) {
        try {
            TravailleurService travailleurService = new TravailleurService(); // Create an instance of TravailleurService
            List<Travailleur> travailleurs = travailleurService.recuperer(); // Call recuperer on the instance

            travailleurs.sort(Comparator.comparingInt(Travailleur::getAge)); // Sort the list of travailleurs by age

            table_id.getItems().setAll(travailleurs); // Update the TableView
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while sorting travailleurs by age.");
            alert.showAndWait();
        }
    }

}
