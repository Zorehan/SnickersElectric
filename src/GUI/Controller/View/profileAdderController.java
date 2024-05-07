package GUI.Controller.View;

import BE.Profile;
import BE.Team;
import GUI.Model.ProfileModel;
import GUI.Model.ProfileTeamModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class profileAdderController implements Initializable {

    // FXML elements
    @FXML private Button btnSave;
    @FXML private TableView<Profile> tblViewProfiles;
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
    private final ProfileModel profileModel = ProfileModel.getInstance();
    private final ProfileTeamModel profileTeamModel = ProfileTeamModel.getInstance();

    // Selected team
    private Team selectedTeam;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize table columns & set table items
        initializeColumns();
        tblViewProfiles.setItems(profileModel.getObservableProfiles());
    }

    // Method for adding a profile to a team
    @FXML
    private void addSelectedProfile() {
        Profile selectedProfile = tblViewProfiles.getSelectionModel().getSelectedItem();
        if (selectedProfile != null && selectedTeam != null) {
            if (isProfileAlreadyInTeam(selectedProfile, selectedTeam)) {
                showErrorDialog("Selected profile is already associated with the team.");
            } else {
                profileTeamModel.addProfileToTeam(selectedTeam.getId(), selectedProfile.getId());

                Stage stage = (Stage) btnSave.getScene().getWindow();
                stage.close();
            }
        }
    }

    private boolean isProfileAlreadyInTeam(Profile profile, Team team) {
        // Get the list of profiles associated with the selected team from the profileTeamModel
        List<Profile> profilesInTeam = profileTeamModel.getProfilesForTeam(team.getId());

        // Check if the selected profile is already in the list
        for (Profile p : profilesInTeam) {
            if (p.getId() == profile.getId()) {
                return true; // Profile already in the team
            }
        }
        return false; // Profile not in the team
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

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}