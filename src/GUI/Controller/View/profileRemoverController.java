package GUI.Controller.View;

import BE.Profile;
import BE.Team;
import GUI.Model.ProfileTeamModel;
import GUI.Model.TeamModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class profileRemoverController implements Initializable {

    // FXML elements
    @FXML private Button btnDelete;
    @FXML private TableView<Profile> tblViewTeamMembers;
    @FXML private TableColumn<Profile, String> nameColumn;
    @FXML private TableColumn<Profile, String> countryColumn;
    @FXML private TableColumn<Profile, Double> annualSalaryColumn;
    @FXML private TableColumn<Profile, Double> fixedAnnualAmountColumn;
    @FXML private TableColumn<Profile, Double> annualWorkingHoursColumn;
    @FXML private TableColumn<Profile, Double> overheadColumn;
    @FXML private TableColumn<Profile, Double> utilizationColumn;
    @FXML private TableColumn<Profile, String> typeColumn;
    @FXML private TableColumn<Profile, Double> hourlyRateColumn;
    @FXML private TableColumn<Profile, Double> dailyRateColumn;

    // Models
    private final TeamModel teamModel = TeamModel.getInstance();
    private final ProfileTeamModel profileTeamModel = ProfileTeamModel.getInstance();

    // Selected team
    private Team selectedTeam;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize table columns
        initializeColumns();

        // Get the selected team from the model
        Team selectedTeam = teamModel.getChosenTeam();

        // Populate the table with the profiles of the selected team
        if (selectedTeam != null) {
            tblViewTeamMembers.setItems(profileTeamModel.getProfilesForTeam(selectedTeam.getId()));
        }
    }

    // Method for deleting a profile from a team
    @FXML
    private void deleteSelectedProfile() {
        Profile selectedProfile = tblViewTeamMembers.getSelectionModel().getSelectedItem();
        if (selectedProfile != null && selectedTeam != null) {
            profileTeamModel.removeProfileFromTeam(selectedProfile.getId(), selectedTeam.getId());

            Stage stage = (Stage) btnDelete.getScene().getWindow();
            stage.close();
        }
    }

    // Method for initializing columns
    private void initializeColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        annualSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("annualSalary"));
        fixedAnnualAmountColumn.setCellValueFactory(new PropertyValueFactory<>("annualAmount"));
        annualWorkingHoursColumn.setCellValueFactory(new PropertyValueFactory<>("workHours"));
        overheadColumn.setCellValueFactory(new PropertyValueFactory<>("overheadPercent"));
        utilizationColumn.setCellValueFactory(new PropertyValueFactory<>("utilizationPercent"));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().toString()));
        hourlyRateColumn.setCellValueFactory(new PropertyValueFactory<>("hourlyRate"));
        dailyRateColumn.setCellValueFactory(new PropertyValueFactory<>("dailyRate"));
    }

    // Simple setter
    void setSelectedTeam(Team team) {
        this.selectedTeam = team;
    }
}
