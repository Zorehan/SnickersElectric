package GUI.Controller;

import BE.Profile;
import BE.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class teamEditorController implements Initializable {

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



    private Profile profile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeCountries();
        initializeProfileTypes();
    }


    // Initializes all countries with use of getISOCountries();
    private void initializeCountries() {
        String[] countryCodes = Locale.getISOCountries();
        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            comboBoxCountry.getItems().add(locale.getDisplayCountry());
        }
    }

    private void initializeProfileTypes() {
        for (Profile.ProfileType type : Profile.ProfileType.values()) {
            comboBoxType.getItems().add(type.getDisplayName());
        }
    }

    @FXML
    private void saveInformation() {

        String name = txtFieldName.getText();
        String country = comboBoxCountry.getValue();
        //Team.TeamType type = Team.TeamType.valueOf(comboBoxType.getValue());

        Team team = new Team(-1, name);

        // TODO: Add CRUD create


        // Close down window
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }



}
