package GUI.Controller;

import BE.Profile;
import BE.Team;
import GUI.Model.TeamModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class teamCreatorController implements Initializable {

    @FXML
    private TextField txtFieldName;
    @FXML
    private TextField txtFieldWorkingHours;
    @FXML
    private TextField txtFieldOverhead;
    @FXML
    private TextField txtFieldUtilization;
    @FXML
    private TextField txtFieldAnnualAmount;
    @FXML
    private TextField txtFieldAnnualSalary;
    @FXML
    private ComboBox<String> comboBoxCountry;
    @FXML
    private ComboBox<String> comboBoxType;
    @FXML
    private Button btnSave;

    private Team team;

    private Team chosenTeam;

    private final TeamModel teamModel = TeamModel.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCountries();
        initializeTeamTypes();
    }


    // Initializes all countries with use of getISOCountries();
    private void initializeCountries() {
        String[] countryCodes = Locale.getISOCountries();
        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            comboBoxCountry.getItems().add(locale.getDisplayCountry());
        }
    }

    private void initializeTeamTypes() {
        for (Team.TeamType type : Team.TeamType.values()) {
            comboBoxType.getItems().add(type.getDisplayName());
        }
    }

    @FXML
    private void saveInformation() {

        // Save all information from text fields and create team
        String name = txtFieldName.getText();
        String country = comboBoxCountry.getValue();
        Team.TeamType actualType = null;

        for (Team.TeamType type : Team.TeamType.values()) {
            if (type.getDisplayName().equals(comboBoxType.getValue())) {
                actualType = type;
                break;
            }
        }

        if (actualType == null) {
            System.err.println("Invalid team type: " + comboBoxType.getValue());
        }

        Team team = new Team(-1, name, country,actualType);

        teamModel.createTeam(team);


        // Close down window
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

}
