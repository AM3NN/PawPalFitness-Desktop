package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import Models.Animal;
import Models.Favori;
import Services.AnimalService;
import Services.FavoriService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;

public class FavorisAnimal {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Qu;

    @FXML
    private Button SU_Animal;

    @FXML
    private Button btn_profile;

    @FXML
    private VBox Item;

    private FavoriService fs=new FavoriService();

    @FXML
    void IntPAnimaux(ActionEvent event) throws IOException{
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/TypeAnimal.fxml")));
        Scene scene = new Scene(root,800,550);
        stage.setScene(scene);
    }

    @FXML
    void Quitter(ActionEvent event)throws IOException {
        Stage stage = (Stage) SU_Animal.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/TypeAnimal.fxml")));
        Scene scene = new Scene(root,800,550);
        stage.setScene(scene);
    }

    @FXML
    void initialize()throws IOException, SQLException {
        List<Favori> fx = fs.afficherFAnimal();
        if (!fx.isEmpty()){
            for (int i=0;i< fx.size();i++){
                HBox hb = new HBox();

                Favori f =fx.get(i);
                Label nom = new Label("Nom: " + f.getNoma());
                String n=f.getNoma();
                nom.setFont(Font.font(14));
                nom.setStyle("-fx-font-weight: bold;");

                Region region2 = new Region();
                region2.setPrefSize(20,20);

                Image fv =new Image("/coeur.png");
                region2.setBackground(new Background(new BackgroundFill(new ImagePattern(fv),null,null)));

                hb.getChildren().addAll(nom,region2);
                hb.setSpacing(60);
                DropShadow ds =new DropShadow();
                ds.setRadius(5);
                ds.setOffsetX(3);
                ds.setOffsetY(3);
                hb.setEffect(ds);
                hb.setStyle("-fx-border-color: black; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-style: solid; " +
                        "-fx-padding: 10px;" +
                        "-fx-margin-bottom: 10px;" +
                        "-fx-border-radius: 4;");
                Item.getChildren().add(hb);
                VBox.setMargin(Item, new Insets(0,0,0,30));

                region2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        FavoriService fs =new FavoriService();
                        try{
                            fs.supprimerFavori(n);
                            hb.getChildren().clear();
                            hb.setStyle(null);
                            }
                        catch (SQLException e) {
                            System.out.println(e.getMessage());}
                    }
                });

            }
        }
        }

}
