package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import utils.MyDabase;
//import org.json.simple.JSONObject;
//import java.net.MalformedURLException;
//import Utils.APIConnector;
//import org.json.simple.parser.JSONParser;
//import javafx.scene.control.Label;

public class TypeAnimal {
    private int userId;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button SU_Animal;

    @FXML
    private Button btn_profile;

    @FXML
    private Button Qu;

    @FXML
    private TextField city;

    @FXML
    private TextArea temp;

    @FXML
    void Ajouter(ActionEvent event)throws IOException {
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AjoutAnimal.fxml")));
        Scene scene = new Scene(root,800,550);
        stage.setScene(scene);
    }

    @FXML
    void Chats(MouseEvent event) throws IOException {
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/SU_Animaux.fxml")));
        Scene scene = new Scene(root,800,550);
        stage.setScene(scene);
    }

    @FXML
    void Chiens(MouseEvent event) throws IOException {
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/SU_AnimauxChiens.fxml")));
        Scene scene = new Scene(root,800,550);
        stage.setScene(scene);
    }

    @FXML
    void IntPAnimaux(ActionEvent event) throws IOException {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        /*ICONE alert a faire*/
        alert.setContentText("Veillez choisir l'un des catégories affiché pour voir vos animaux");
        alert.show();
    }

    @FXML
    void Quitter(ActionEvent event) throws IOException{
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/HomeT.fxml")));
        Scene scene = new Scene(root,800,550);
        stage.setScene(scene);
    }

    @FXML
    void listefavoris(MouseEvent event)throws IOException {
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FavorisAnimal.fxml")));
        Scene scene = new Scene(root,800,550);
        stage.setScene(scene);
    }

    //*************** WEATHER API ******************
    /*@FXML
    void getWeatherData(ActionEvent event) {
        String cit = city.getText();
        if (!cit.isEmpty()) {
            JSONObject weatherData = getWeatherDataFromAPI(cit);
            if (weatherData != null) {
                updateUI(weatherData);
            } else {
                System.out.println("Failed to retrieve weather data.");
            }
        } else {
            System.out.println("Please enter a city name.");
        }
    }

    private JSONObject getWeatherDataFromAPI(String city) {
        try {
            String apiKey = "bf46852e640a439382e104225240303";
            String apiUrl = "http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + city;
            String responseData = new APIConnector(apiUrl).fetchData();
            if (responseData != null) {
                JSONParser parser = new JSONParser();
                return (JSONObject) parser.parse(responseData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateUI(JSONObject weatherData) {
        JSONObject current = (JSONObject) weatherData.get("current");
        if (current != null) {
            String temperature = current.get("temp_c").toString();
            String humidity = current.get("humidity").toString();
            String condition = ((JSONObject) current.get("condition")).get("text").toString();

            temperatureLabel.setText("Temperature: " + temperature + "°C");
            humidityLabel.setText("Humidity: " + humidity + "%");
            conditionLabel.setText("Condition: " + condition);
        }
    }*/


    //****************************************
    @FXML
    void initialize() {
    }

    public void getWeatherData(MouseEvent mouseEvent) {
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    @FXML
    void Home(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
            Parent profileRoot = loader.load();
            Home profileController = loader.getController();
            profileController.setUserId(userId);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(profileRoot));
            primaryStage.setTitle("Home");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Profile Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void CheckProfile(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
            Parent profileRoot = loader.load();
            Profile profileController = loader.getController();
            profileController.setUserId(userId);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(profileRoot));
            primaryStage.setTitle("Home");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Planning(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShoppingPage.fxml"));
            Parent profileRoot = loader.load();
            ShoppingPage profileController = loader.getController();
            profileController.setUserId(userId);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(profileRoot));
            primaryStage.setTitle("Planning");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Produit(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduitUser.fxml"));
            Parent profileRoot = loader.load();
            AfficherProduitUser shoppingPageController = loader.getController();
            shoppingPageController.setUserId(userId);
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(profileRoot));
            primaryStage.setTitle("Home");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
