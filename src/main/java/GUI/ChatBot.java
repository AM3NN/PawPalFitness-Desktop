package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.ChatGPTAPIExample;

public class ChatBot {

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
    private TextArea textAnswer;

    @FXML
    private TextField textPrompt;

    @FXML
    void sendPrompt(ActionEvent event) {
        // Get the user prompt from the text field
        String prompt = textPrompt.getText();
        // Call the chatGPT method from the ChatGPTAPIExample class
        String response = ChatGPTAPIExample.chatGPT(prompt);
        // Set the response to the text area
        textAnswer.setText(response);
    }

    @FXML
    void initialize() {
        // Event handler for the Qu button (if needed)
        // Qu.setOnAction(event -> {
        //     // Handle button click for Qu button
        // });

        // Event handler for the SU_Animal button (if needed)
        // SU_Animal.setOnAction(event -> {
        //     // Handle button click for SU_Animal button
        // });

        // Event handler for the btn_profile button (if needed)
        // btn_profile.setOnAction(event -> {
        //     // Handle button click for btn_profile button
        // });
    }

}