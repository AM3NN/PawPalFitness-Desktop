package GUI;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.time.LocalTime;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Reservation;
import services.ReservationService;
import javafx.scene.Node;


public class IntAcceuil {


    @FXML
    private ResourceBundle resources;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private URL location;
    @FXML
    private TextField search;


    @FXML
    private Button Bstat;
    @FXML
    private Label Abon_SU;

    @FXML
    private Label Animaux;

    @FXML
    private Label Planning_SU;

    @FXML
    private Label Produits_SU;

    @FXML
    private Label Profile_SU;

    @FXML
    private ComboBox<String> categoryField;

    @FXML
    private TextField dateField;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField durationField;

    @FXML
    private TextField endTimeField;

    @FXML
    private Button insertButton;

    @FXML
    private TextField placesField;

    @FXML
    private TextField pricingField;

    @FXML
    private TextField reservationField;

    @FXML
    private TextField startTimeField;

    @FXML
    private TextField statusField;

    @FXML
    private Button updateButton;

    @FXML
    private Button Bpdf;


    @FXML
    ReservationService reservationService = new ReservationService();


    @FXML
    private TableView<Reservation> reservationTable;
    @FXML
    private TableColumn<Reservation, Integer> reservationIdColumn;
    @FXML
    private TableColumn<Reservation, Integer> placesColumn;
    @FXML
    private TableColumn<Reservation, String> categoryColumn;
    @FXML
    private TableColumn<Reservation, String> dateColumn;
    @FXML
    private TableColumn<Reservation, String> startTimeColumn;
    @FXML
    private TableColumn<Reservation, String> endTimeColumn;
    @FXML
    private TableColumn<Reservation, String> statusColumn;
    @FXML
    private TableColumn<Reservation, Integer> durationColumn;
    @FXML
    private TableColumn<Reservation, Integer> pricingColumn;


    @FXML
    private void handleBstatButton(ActionEvent event) {
        try {
            // Load the stat.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/chart.fxml"));
            Parent root = loader.load();

            // Get the controller
            Chart controller = loader.getController();

// Call the method to populate the chart
            controller.initialize();

            // Create a new stage for the stat view
            Stage stage = new Stage();
            stage.setTitle("Statistics");
            stage.setScene(new Scene(root));
            stage.show();

            // Optionally, close the current stage (if needed)
            //((Stage) Bstat.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void AfficherProfile(MouseEvent event) {

    }

    public List<Reservation> getTableReservations() {
        return reservationTable.getItems();
    }

    @FXML
    void modifyReservation(ActionEvent event) {
        try {
            int index = reservationTable.getSelectionModel().getSelectedIndex();
            Reservation selectedReservation = reservationTable.getItems().get(index);

            int reservationID = Integer.parseInt(reservationField.getText());
            ReservationService reservationService = new ReservationService();
            Reservation updatedReservation = new Reservation();

            // Get the category from the ComboBox
            String category = categoryField.getValue();
            if (category == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Veuillez sélectionner une catégorie");
                alert.show();
            } else {
                // Set the properties of the updated Reservation
                updatedReservation.setReservationID(reservationID);
                updatedReservation.setPlaces(Integer.parseInt(placesField.getText()));
                updatedReservation.setCategory(category);
                updatedReservation.setDate(dateField.getText());
                updatedReservation.setStartTime(startTimeField.getText());
                updatedReservation.setEndTime(endTimeField.getText());
                updatedReservation.setStatus(statusField.getText());
                updatedReservation.setDuration(Integer.parseInt(durationField.getText()));
                updatedReservation.setPricing(Integer.parseInt(pricingField.getText()));

                // Update the Reservation in the database
                reservationService.modifier(updatedReservation);

                // Update the Reservation object in the TableView
                selectedReservation.setPlaces(updatedReservation.getPlaces());
                selectedReservation.setCategory(updatedReservation.getCategory());
                selectedReservation.setDate(updatedReservation.getDate());
                selectedReservation.setStartTime(updatedReservation.getStartTime());
                selectedReservation.setEndTime(updatedReservation.getEndTime());
                selectedReservation.setStatus(updatedReservation.getStatus());
                selectedReservation.setDuration(updatedReservation.getDuration());
                selectedReservation.setPricing(updatedReservation.getPricing());

                // Reload data from the database
                refreshTable();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("La réservation a été mise à jour avec succès");
                alert.show();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez entrer des nombres valides pour les champs appropriés");
            alert.show();
        }
    }


    @FXML
    void ajouterReservation(ActionEvent event) {
        try {
            int places = Integer.parseInt(placesField.getText());
            int pricing = Integer.parseInt(pricingField.getText());

            // Get the category from the ComboBox
            String category = categoryField.getValue();
            if (category == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Veuillez sélectionner une catégorie");
                alert.show();
            } else {
                Reservation reservation = new Reservation();

                // Set the properties of the Reservation
                reservation.setPlaces(places);
                reservation.setCategory(category);
                reservation.setDate(dateField.getText());
                reservation.setStartTime(startTimeField.getText());
                reservation.setEndTime(endTimeField.getText());
                reservation.setStatus(statusField.getText());
                reservation.setDuration(Integer.parseInt(durationField.getText()));
                reservation.setPricing(pricing);

                // Add the Reservation to the database
                ReservationService reservationService = new ReservationService();
                reservationService.ajouter(reservation);

                // Reload data from the database
                refreshTable();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("La réservation a été ajoutée avec succès");
                alert.show();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez entrer un nombre valide pour les places et le prix");
            alert.show();
        }
    }


    @FXML
    public void initialize() {

        reservationIdColumn.setCellValueFactory(new PropertyValueFactory<>("reservationID"));
        placesColumn.setCellValueFactory(new PropertyValueFactory<>("places"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        pricingColumn.setCellValueFactory(new PropertyValueFactory<>("pricing"));

        ReservationService reservationService = new ReservationService();
        try {
            reservationTable.setItems(FXCollections.observableArrayList(reservationService.recuperer()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<Reservation> reservations;
        try {
            ReservationService reservationService1 = new ReservationService();
            reservations = FXCollections.observableArrayList(reservationService1.recuperer());
            reservationTable.setItems(reservations);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        reservationTable.setRowFactory(tv -> {
            TableRow<Reservation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Reservation rowData = row.getItem();
                    // Load the data into the TextFields
                    reservationField.setText(String.valueOf(rowData.getReservationID()));
                    placesField.setText(String.valueOf(rowData.getPlaces()));
                    categoryField.setValue(rowData.getCategory());
                    dateField.setText(rowData.getDate());
                    startTimeField.setText(rowData.getStartTime());
                    endTimeField.setText(rowData.getEndTime());
                    statusField.setText(rowData.getStatus());
                    durationField.setText(String.valueOf(rowData.getDuration()));
                    pricingField.setText(String.valueOf(rowData.getPricing()));
                }
            });
            return row;
        });

        deleteButton.setOnAction(event -> {
            Reservation selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
            if (selectedReservation != null) {
                try {
                    ReservationService reservationService1 = new ReservationService();
                    reservationService.supprimer(selectedReservation.getReservationID());
                    reservationTable.getItems().remove(selectedReservation);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("La réservation a été supprimée avec succès");
                    alert.show();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Veuillez sélectionner une réservation à supprimer");
                alert.show();
            }
        });
        refreshTable();

        categoryField.getItems().addAll("Gym", "Boxing", "Crossfit", "Cycling", "Dance", "Pilates", "Yoga");

        // Create the listener
        ChangeListener<Boolean> dateFieldFocusListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {  // when focus lost
                    if (!isValidDate(dateField.getText())) {
                        // Remove the listener
                        dateField.focusedProperty().removeListener(this);

                        // Show the alert
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Invalid date format. Please enter the date in 'dd-MM-yyyy' format.");
                        alert.show();

                        // Add the listener back
                        dateField.focusedProperty().addListener(this);
                    }
                }
            }
        };
        // Add input validation for startTimeField and endTimeField
        startTimeField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {  // when focus lost
                if (!isValidTimeFormat(startTimeField.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Invalid time format. Please enter the time in 'hh:mm' format.");
                    alert.show();
                }
            }
        });

        endTimeField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {  // when focus lost
                if (!isValidTimeFormat(endTimeField.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Invalid time format. Please enter the time in 'hh:mm' format.");
                    alert.show();
                }
            }
        });

        // Add input validation for statusField and durationField
        statusField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && statusField.getText().isEmpty()) {  // when focus lost
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The status field should not be empty.");
                alert.show();
            }
        });

        durationField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && durationField.getText().isEmpty()) {  // when focus lost
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The duration field should not be empty.");
                alert.show();
            }
        });

// Add the listener to the dateField
        dateField.focusedProperty().addListener(dateFieldFocusListener);

        ObservableList<Reservation> res;

        try {
            ReservationService userService = new ReservationService();
            res = FXCollections.observableArrayList(userService.recuperer());
            reservationTable.setItems(res);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        FilteredList<Reservation> filteredData = new FilteredList<>(res, b -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Reservation -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                return Reservation.getCategory().toLowerCase().indexOf(searchKeyword) > -1;
            });
        });
        SortedList<Reservation> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(reservationTable.comparatorProperty());
        reservationTable.setItems(sortedData);
    }


    private boolean isValidCategory(String category) {
        // Define the valid categories
        List<String> validCategories = Arrays.asList("Gym", "Boxing", "Cardio", "Crossfit", "Cycling", "Dance", "Pilates", "Yoga");

        // Check if the category is valid
        return validCategories.contains(category);
    }

    private void refreshTable() {
        ReservationService reservationService = new ReservationService();
        try {
            reservationTable.setItems(FXCollections.observableArrayList(reservationService.recuperer()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidDate(String date) {
        // Implement your date validation logic here
        // For example, you can check if the date is in the correct format
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            return true;
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid date format. Please enter the date in 'dd-MM-yyyy' format.");
            alert.show();
            return false;
        }
    }

    private boolean isValidTimeFormat(String time) {
        // Check if the time is in the correct format
        try {
            LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


    public List<String> getCategories() {
        return categoryField.getItems();
    }

    @FXML
    void generatePdfButtonClicked(ActionEvent actionEvent) {
        // Handle the button click to generate the PDF
        reservationService.generatePdfFromTableView(reservationTable.getItems()); // Pass the list of users

        // Open the PDF file
        File file = new File("table_view.pdf");
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (file.exists()) {
                try {
                    desktop.open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }


    public void CheckRes(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReservation.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Reservation");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void CheckUsers(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPersonne.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Users");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkWorkers(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherTravailleur.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Travailleur");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void produit(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RecupererProduitAdmin.fxml"));
            Parent signInRoot = loader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(signInRoot));
            primaryStage.setTitle("Produit");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
