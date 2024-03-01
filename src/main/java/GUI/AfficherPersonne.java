package GUI;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Personne;
import services.PersonneService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AfficherPersonne {
    public Hyperlink btn_home;
    public Button btn_generatePDF;
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
    private TextField searchField;

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
            ObservableList<Personne> observablePersonnes = FXCollections.observableArrayList(personnes);
            table_id.setItems(observablePersonnes);

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
                            observablePersonnes.remove(selectedPersonne);
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
        ObservableList<Personne> personnes = FXCollections.observableArrayList(personneService.recuperer());
        FilteredList<Personne> filteredData = new FilteredList<>(personnes, p -> true);

        if (searchText == null || searchText.trim().isEmpty()) {
            table_id.setItems(personnes);
        } else {
            filteredData.setPredicate(personne -> personneContainsSearchText(personne, searchText));
            table_id.setItems(filteredData);
        }
    }

    private boolean personneContainsSearchText(Personne personne, String searchText) {
        // Check if any field of the Personne object contains the search text
        return personne.getNom().toLowerCase().contains(searchText.toLowerCase()) ||
                personne.getPrenom().toLowerCase().contains(searchText.toLowerCase()) ||
                String.valueOf(personne.getAge()).toLowerCase().contains(searchText.toLowerCase()) ||
                personne.getRegion().toLowerCase().contains(searchText.toLowerCase()) ||
                personne.getEmail().toLowerCase().contains(searchText.toLowerCase()) ||
                personne.getPassword().toLowerCase().contains(searchText.toLowerCase());
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

    @FXML
    private void generatePDF() {
        Document document = new Document();

        try {
            // Create PDF writer
            PdfWriter.getInstance(document, new FileOutputStream(System.getProperty("user.home") + "/Desktop/personnes.pdf"));

            document.open();

            // Add title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Paragraph title = new Paragraph("Liste des Personnes", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Create table
            PdfPTable table = new PdfPTable(7); // 7 columns for your Personne attributes
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Define column widths
            float[] columnWidths = {1f, 3f, 3f, 1.5f, 3f, 4f, 3f};
            table.setWidths(columnWidths);

            // Add table header
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            PdfPCell header;
            for (String headerText : new String[]{"ID", "Nom", "Prenom", "Age", "Region", "Email", "Password"}) {
                header = new PdfPCell();
                header.setBackgroundColor(BaseColor.DARK_GRAY);
                header.setBorderWidth(1);
                header.setPadding(5);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setVerticalAlignment(Element.ALIGN_CENTER);
                header.setPhrase(new Phrase(headerText, headerFont));
                table.addCell(header);
            }

            // Retrieve all personnes
            List<Personne> personnes = personneService.recuperer();

            // Add each personne's information to the PDF
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
            for (Personne personne : personnes) {
                table.addCell(new PdfPCell(new Phrase(String.valueOf(personne.getId()), cellFont)));
                table.addCell(new PdfPCell(new Phrase(personne.getNom(), cellFont)));
                table.addCell(new PdfPCell(new Phrase(personne.getPrenom(), cellFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(personne.getAge()), cellFont)));
                table.addCell(new PdfPCell(new Phrase(personne.getRegion(), cellFont)));
                table.addCell(new PdfPCell(new Phrase(personne.getEmail(), cellFont)));
                table.addCell(new PdfPCell(new Phrase(personne.getPassword(), cellFont)));
            }
            document.add(table);
            document.close();

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF Generated");
            alert.setHeaderText(null);
            alert.setContentText("PDF generated successfully!");
            alert.showAndWait();
        } catch (DocumentException | IOException | SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while generating the PDF.");
            alert.showAndWait();
        }
    }

    public void triByAge(ActionEvent actionEvent) {
        try {
            List<Personne> personnes = personneService.recuperer();
            personnes.sort(Comparator.comparingInt(Personne::getAge));

            table_id.getItems().setAll(personnes);
        } catch (SQLException e) {
            e.printStackTrace();
            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while sorting persons by age.");
            alert.showAndWait();
        }
    }
}