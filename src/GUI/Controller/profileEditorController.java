package GUI.Controller;

import BE.Profile;
import GUI.Model.ProfileModel;
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

public class profileEditorController implements Initializable {
    @FXML
    private TextField txtFieldAnnualSalary;
    @FXML
    private TextField txtFieldAnnualAmount;
    @FXML
    private TextField txtFieldUtilization;
    @FXML
    private TextField txtFieldName;
    @FXML
    private TextField txtFieldWorkingHours;
    @FXML
    private TextField txtFieldOverhead;
    @FXML
    private ComboBox<String> comboBoxCountry;

    @FXML
    private ComboBox<String> comboBoxType;

    @FXML
    private Button btnSave;

    @FXML
    private Label lblProfileName;

    private Profile profile;

    private Profile chosenProfile;

    private ProfileModel profileModel = ProfileModel.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        chosenProfile = profileModel.getChosenProfile();
        lblProfileName.setText("Profile: " + chosenProfile.getName());
        txtFieldAnnualAmount.setText(String.valueOf(chosenProfile.getAnnualAmount()));
        txtFieldAnnualSalary.setText(String.valueOf(chosenProfile.getAnnualSalary()));
        txtFieldUtilization.setText(String.valueOf(chosenProfile.getUtilizationPercent()));
        txtFieldName.setText(chosenProfile.getName());
        txtFieldWorkingHours.setText(String.valueOf(chosenProfile.getWorkHours()));
        txtFieldOverhead.setText(String.valueOf(chosenProfile.getOverheadPercent()));
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
        chosenProfile.setWorkHours(Double.parseDouble(txtFieldWorkingHours.getText()));
        chosenProfile.setOverheadPercent(Double.parseDouble(txtFieldOverhead.getText()));
        chosenProfile.setUtilizationPercent(Double.parseDouble(txtFieldUtilization.getText()));
        chosenProfile.setAnnualAmount(Double.parseDouble(txtFieldAnnualAmount.getText()));
        chosenProfile.setAnnualSalary(Double.parseDouble(txtFieldAnnualSalary.getText()));
        chosenProfile.setCountry(comboBoxCountry.getValue());

        for (Profile.ProfileType type : Profile.ProfileType.values()) {
            if (type.getDisplayName().equals(comboBoxType.getValue())) {
                chosenProfile.setType(type);
                break;
            }
        }

        try {
            profileModel.updateProfile(chosenProfile);
        } catch (Exception e )
        {
            e.printStackTrace();
        }

        // Close down window
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }



}
