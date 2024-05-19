package GUI.Controller.View;

import DAL.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import util.Exception;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mainViewController {

    @FXML private BorderPane borderPane;

    @FXML private void handleProfilesButtonClick() {
        loadAndViewFXML("../../View/profileView.fxml");
    }

    @FXML private void handleTeamsButtonClick() {
        loadAndViewFXML("../../View/teamView.fxml");
    }

    private void loadAndViewFXML(String fxmlFilePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            borderPane.setCenter(loader.load());
        } catch (IOException e) {
            showAndLogError(new Exception("Error loading FXML file: " + fxmlFilePath, e));
        }
    }

    @FXML private void handleDashboardButtonClick(ActionEvent actionEvent) {
        loadAndViewFXML("../../View/dashboardView.fxml");
    }

    @FXML private void handleScenariosButtonClick(ActionEvent actionEvent) {
        loadAndViewFXML("../../View/scenarioView.fxml");
    }

    @FXML private void handleLogsButtonClick(ActionEvent actionEvent)
    {
        loadAndViewFXML("../../View/logView.fxml");
    }

    private static void showAndLogError(Exception ex) {
        Logger.getLogger(mainViewController.class.getName()).log(Level.SEVERE, null, ex);

        Alert alert = new Alert(Alert.AlertType.ERROR,
                ex.getMessage()
                        + String.format("%n")
                        + "See error log for technical details."
        );
        alert.showAndWait();
    }
}
