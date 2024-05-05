package GUI.Controller.View;

import DAL.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class mainViewController {

    public VBox boxLeftPanel;
    @FXML
    private BorderPane borderPane;

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleProfilesButtonClick() {
        loadAndViewFXML("../../View/profileView.fxml");
    }

    @FXML
    private void handleTeamsButtonClick() {
        loadAndViewFXML("../../View/teamView.fxml");
    }

    private void loadAndViewFXML(String fxmlFilePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            borderPane.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDashboardButtonClick(ActionEvent actionEvent) {
    }

    @FXML
    private void handleScenariosButtonClick(ActionEvent actionEvent) {
        loadAndViewFXML("../../View/scenarioView.fxml");
    }
}
