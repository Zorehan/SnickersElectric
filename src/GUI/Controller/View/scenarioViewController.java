package GUI.Controller.View;

import BE.Profile;
import BE.Scenario;
import GUI.Model.ScenarioModel;
import GUI.Model.ScenarioProfileModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
            }
        });
    }

    @FXML
    private void openScenarioCreator(ActionEvent actionEvent) {
        openNewWindow("../../View/scenarioCreator.fxml");
    }

    @FXML
    private void openScenarioEditor(ActionEvent actionEvent) {
        openNewWindow("../../View/scenarioEditor.fxml");
    }

    @FXML
    private void removeScenario(ActionEvent actionEvent) {
        //TODO dette kræver at man loader en list af alle profiler. Skal nok få det ordnet engang.
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
}
