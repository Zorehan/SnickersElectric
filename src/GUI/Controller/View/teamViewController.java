package GUI.Controller.View;

import BE.Profile;
import BE.Team;
import GUI.Model.ProfileModel;
import GUI.Model.TeamModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class teamViewController implements Initializable {

    @FXML
    private TableView<Team> tblViewTeams;
    @FXML
    private TableColumn<Team, String> nameColumn;

    @FXML
    private TableColumn<Team, String> countryColumn;

    @FXML
    private TableColumn<Team, Double> annualSalaryColumn;

    @FXML
    private TableColumn<Team, Double> fixedAnnualAmountColumn;

    @FXML
    private TableColumn<Team, Double> annualWorkingHoursColumn;

    @FXML
    private TableColumn<Team, Integer> overheadColumn;

    @FXML
    private TableColumn<Team, Double> utilizationColumn;

    @FXML
    private TableColumn<Team, String> typeColumn;

    @FXML
    private TableColumn<Team, Double> hourlyRateColumn;

    @FXML
    private TableColumn<Team,Double> dailyRateColumn;

    private final TeamModel teamModel = TeamModel.getInstance();

    @FXML
    private void openTeamEditor() {
        openNewWindow("../../View/teamEditor.fxml");
    }

    @FXML
    private void openTeamCreator() {
        openNewWindow("../../View/teamCreator.fxml");
    }

    @FXML
    private void openNewWindow(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Schneider Electric");

            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteSelectedTeam() {
        Team selectedTeam = tblViewTeams.getSelectionModel().getSelectedItem();
        if (selectedTeam != null) {
            teamModel.deleteTeam(selectedTeam);
        } else {
            // If no profile is selected, show an error pop-up
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a team to delete.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Det her burde meget gerne virke ligeså snart vi har en modelklasse knyttet til og noget crud

        tblViewTeams.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> teamModel.setChosenTeam(newValue));

        tblViewTeams.setItems(teamModel.getObservableTeams());
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().toString()));
        /*annualSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("annualSalary"));
        fixedAnnualAmountColumn.setCellValueFactory(new PropertyValueFactory<>("annualAmount"));
        annualWorkingHoursColumn.setCellValueFactory(new PropertyValueFactory<>("workHours"));
        overheadColumn.setCellValueFactory(new PropertyValueFactory<>("overheadPercent"));
        utilizationColumn.setCellValueFactory(new PropertyValueFactory<>("utilizationPercent"));
        hourlyRateColumn.setCellValueFactory(new PropertyValueFactory<>("hourlyRate"));
        dailyRateColumn.setCellValueFactory(new PropertyValueFactory<>("dailyRate"));*/
    }
}

