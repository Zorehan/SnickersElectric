package GUI.Controller.View;

import DAL.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import util.Exception;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mainViewController implements Initializable{

    @FXML private Button btnDashboard;
    @FXML private Button btnProfiles;
    @FXML private Button btnTeams;
    @FXML private Button btnScenarios;
    @FXML private Button btnLogs;
    @FXML private Button btnStatistics;
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


    private void setButtonGraphic(Button button, String imagePath) {
            Image image = new Image(imagePath);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(32);  // Set desired height
            imageView.setFitWidth(32);   // Set desired width
            button.setGraphic(imageView);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadAndViewFXML("../../View/dashboardView.fxml");
        setButtonGraphic(btnDashboard, "icons/dashboard.png");
        setButtonGraphic(btnProfiles, "icons/person.png");
        setButtonGraphic(btnTeams, "icons/teams.png");
        setButtonGraphic(btnScenarios, "icons/scenario.png");
        setButtonGraphic(btnLogs, "icons/logs.png");
        setButtonGraphic(btnStatistics, "icons/statistics.png");
    }
}
