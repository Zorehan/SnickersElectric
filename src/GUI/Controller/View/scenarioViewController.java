package GUI.Controller.View;

import BE.Profile;
import BE.Scenario;
import BE.Team;
import GUI.Model.ScenarioModel;
import GUI.Model.ScenarioProfileModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class scenarioViewController implements Initializable {
    private ScenarioModel scenarioModel = ScenarioModel.getInstance();
    private ScenarioProfileModel scenarioProfileModel = ScenarioProfileModel.getInstance();
    @FXML private TableView<Scenario> tblViewScenario;
    @FXML private TableColumn<Scenario, String> colName;
    @FXML private TableColumn<Scenario, Double> colHourlyRate;
    @FXML private TableColumn<Scenario, Double> colDailyRate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblViewScenario.setItems(scenarioModel.getObservableScenarios());
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        //TODO Muligvis tilføj "amount of employees"
        //colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colHourlyRate.setCellValueFactory(new PropertyValueFactory<>("hourlyRate"));
        colDailyRate.setCellValueFactory(new PropertyValueFactory<>("dailyRate"));

        tblViewScenario.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                scenarioModel.setScenario(newValue);
                addRightClickFunctionality();
            }
        });
    }

    @FXML
    private void openScenarioCreator() {
        openNewWindow("../../View/scenarioCreator.fxml");
    }

    @FXML
    private void openScenarioEditor() {
        openNewWindow("../../View/scenarioEditor.fxml");
    }

    @FXML
    private void removeScenario() {
        Scenario scenario = tblViewScenario.getSelectionModel().getSelectedItem();
        if (scenario != null) {
            // Show confirmation dialog
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Confirm Deletion");
            confirmation.setContentText("Are you sure you want to delete the selected scenario?");

            // Show the dialog and wait for user response
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // User confirmed deletion, proceed with deletion
                    scenarioModel.deleteScenario(scenario);
                }
            });
        } else {
            // If no scenario is selected, show an error pop-up
            showErrorDialog("Please select a scenario to delete.");
        }
    }

    private void openNewWindow(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Schneider Electric");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void addRightClickFunctionality() {
        Scenario scenario = scenarioModel.getScenario();
        ContextMenu contextMenu = new ContextMenu();

        // Define menu items
        MenuItem margin = new MenuItem("Margin: " + scenario.getGrossMargin() + "%");
        MenuItem markup = new MenuItem("Markup: " + scenario.getMarkup() + "%");
        MenuItem workHours = new MenuItem("Work Hours (Per Person): " + scenario.getWorkHours());
        MenuItem editMenuItem = new MenuItem("Edit Scenario");
        MenuItem deleteMenuItem = new MenuItem("Delete Scenario");

        // Add menu items to context menu
        contextMenu.getItems().addAll(margin, markup, workHours, editMenuItem, deleteMenuItem);
        margin.setStyle("fx-background-color: black");

        editMenuItem.setOnAction(event -> openScenarioEditor());
        deleteMenuItem.setOnAction(event -> removeScenario());

        // Associate context menu with table view
        tblViewScenario.setContextMenu(contextMenu);
    }
}
