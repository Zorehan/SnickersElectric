package GUI.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class mainViewController {

    @FXML
    private BorderPane borderPane;
    @FXML
    private Button btnProfiles;
    @FXML
    private Button btnTeams;

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleProfilesButtonClick() {
        // Load the profileView.fxml file and set it as the center content
        loadAndViewFXML("../View/profileView.fxml");
    }

    @FXML
    private void handleTeamsButtonClick() {
        // Load the teamsView.fxml file and set it as the center content
        loadAndViewFXML("../View/teamView.fxml");
    }

    private void loadAndViewFXML(String fxmlFilePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            borderPane.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
}
