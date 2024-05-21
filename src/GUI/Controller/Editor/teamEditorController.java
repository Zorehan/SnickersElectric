package GUI.Controller.Editor;

import BE.Profile;
import BE.Team;
import GUI.Model.TeamModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class teamEditorController implements Initializable {
    @FXML
    public Label lblTeamName;
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
        chosenTeam = teamModel.getChosenTeam();
        lblTeamName.setText("Team: " + chosenTeam.getName());
        txtFieldName.setText(chosenTeam.getName());


        initializeCountries();
        initializeTeamTypes();
    }


    // Initializes all countries with use of getISOCountries();
    private void initializeCountries() {
        Locale.setDefault(Locale.ENGLISH);
        String[] countryCodes = Locale.getISOCountries();
        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            comboBoxCountry.getItems().add(locale.getDisplayCountry());
            if(locale.getDisplayCountry().equals(chosenTeam.getCountry())){
                comboBoxCountry.getSelectionModel().select(locale.getDisplayCountry());
            }
        }
    }

    private void initializeTeamTypes() {
        for (Team.TeamType type : Team.TeamType.values()) {
            comboBoxType.getItems().add(type.getDisplayName());

            if(type.equals(chosenTeam.getType())){
                comboBoxType.getSelectionModel().select(type.getDisplayName());
            }
        }
    }

    @FXML
    private void saveInformation() {
        chosenTeam.setName(txtFieldName.getText());
        chosenTeam.setCountry(comboBoxCountry.getValue());

        for (Team.TeamType type : Team.TeamType.values()) {
            if (type.getDisplayName().equals(comboBoxType.getValue())) {
                chosenTeam.setType(type);
                break;
            }
        }

        try {
            teamModel.updateTeam(chosenTeam);
        } catch (Exception e )
        {
            e.printStackTrace();
        }

        // Close down window
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }
}
