package GUI;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import models.Animal;
import models.Favori;
import services.AnimalService;
import services.FavoriService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
public class SUAnimaux {

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
    private Button Supp;

    @FXML
    private Button Supp2;

    @FXML
    private Button btn_profile;

    @FXML
    private Button Qu;

    @FXML
    private VBox Item;

    @FXML
    private TextField searchfield;

    @FXML
    private ImageView search;

    @FXML
    private ImageView favorites;

    private AnimalService as=new AnimalService();

    @FXML
    void IntPAnimaux(ActionEvent event) throws IOException {
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
    void Supprimer(ActionEvent event)throws IOException {
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/SupprimerAnimal.fxml")));
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
    void Chercher(MouseEvent event) throws SQLException {
        List<Animal> ax = as.animalParNom(searchfield.getText());
        if (!ax.isEmpty()){
            Item.getChildren().clear();
            VBox rowBox = new VBox();
            VBox rowBox2 = new VBox();
            HBox lBox = new HBox();
            HBox lBox2 = new HBox();
            Animal a = ax.get(0);
            Label nom = new Label("Nom: " + searchfield.getText());
            Label age = new Label("Age: " + a.getAge());
            Label poids = new Label("Poids: " + a.getPoids());
            Label details = new Label("Details: " + a.getDetails());
            Region region = new Region();
            region.setPrefSize(50,50);
            Image im = new Image("/chien.png");
            region.setBackground(new Background(new BackgroundFill(new ImagePattern(im),null,null)));

            lBox2.getChildren().addAll(age,poids);
            lBox2.setSpacing(15);
            rowBox.getChildren().addAll(nom,lBox2,details);
            lBox.getChildren().addAll(region,rowBox);
            lBox.setSpacing(10);
            rowBox2.getChildren().addAll(lBox);
            Item.getChildren().add(rowBox2);
            Item.setSpacing(20);
            VBox.setMargin(Item, new Insets(0,0,0,30));
        }
    }

    @FXML
    void autorech(KeyEvent  event) throws SQLException {
        Item.getChildren().clear();
        List<Animal> ax = as.rechCaractere();
        if (!ax.isEmpty()) {
            for (int i=0;i< ax.size();i++){
                VBox rowBox = new VBox();
                VBox rowBox2 = new VBox();
                HBox lBox = new HBox();
                HBox lBox2 = new HBox();
                Animal a = ax.get(i);
                Label nom = new Label("Nom: " + a.getNom());
                Label age = new Label("Age: " + a.getAge());
                Label poids = new Label("Poids: " + a.getPoids());
                Label details = new Label("Details: " + a.getDetails());
                Region region = new Region();
                region.setPrefSize(50,50);
                Image im = new Image("/chat.png");
                region.setBackground(new Background(new BackgroundFill(new ImagePattern(im),null,null)));

                lBox2.getChildren().addAll(age,poids);
                lBox2.setSpacing(15);
                rowBox.getChildren().addAll(nom,lBox2,details);
                lBox.getChildren().addAll(region,rowBox);
                lBox.setSpacing(10);
                rowBox2.getChildren().addAll(lBox);
                Item.getChildren().add(rowBox2);
                Item.setSpacing(20);
                VBox.setMargin(Item, new Insets(0,0,0,30));
            }
        }
    }

    @FXML
    void Quitter(ActionEvent event)throws IOException {
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/TypeAnimal.fxml")));
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

    @FXML
    void initialize() throws IOException,SQLException{
        List<Animal> ax = as.animalparCategorie();
        if (!ax.isEmpty()) {
            for (int i=0;i< ax.size();i++){
                VBox rowBox = new VBox();
                VBox rowBox2 = new VBox();
                HBox lBox = new HBox();
                HBox lBox2 = new HBox();

                Animal a = ax.get(i);

                Label nom = new Label("Nom: " + a.getNom());
                String n = a.getNom();
                Label age = new Label("Age: " + a.getAge());
                Label poids = new Label("Poids: " + a.getPoids());
                Label details = new Label("Details: " + a.getDetails());
                Region region = new Region();
                Region region2 = new Region();
                region.setPrefSize(50,50);
                region2.setPrefSize(20,20);
                Image im = new Image("/chat.png");
                region.setBackground(new Background(new BackgroundFill(new ImagePattern(im),null,null)));

                Image fv =new Image("/favorie.png");
                region2.setBackground(new Background(new BackgroundFill(new ImagePattern(fv),null,null)));

                Image c =new Image("/coeur.png");
                region2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        region2.setBackground(new Background(new BackgroundFill(new ImagePattern(c),null,null)));

                        FavoriService fs =new FavoriService();
                        Favori f =new Favori();
                        try{
                            fs.ajouterFAnimal(f,n);}
                        catch (SQLException e) {
                            System.out.println(e.getMessage());}
                    }
                });

                lBox2.getChildren().addAll(age,poids);
                lBox2.setSpacing(15);
                rowBox.getChildren().addAll(nom,lBox2,details);
                lBox.getChildren().addAll(region,rowBox,region2);
                lBox.setSpacing(10);
                rowBox2.getChildren().addAll(lBox);
                Item.getChildren().add(rowBox2);
                Item.setSpacing(20);
                VBox.setMargin(Item, new Insets(0,0,0,30));
            }
        }
        else {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            /*ICONE alert*/
            alert.setContentText("Vous n'avez pas de chats pour le moment");
        }
    }

}

