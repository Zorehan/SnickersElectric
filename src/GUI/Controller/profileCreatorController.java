package GUI.Controller;

import BE.Profile;
import GUI.Model.ProfileModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class profileCreatorController implements Initializable {
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

    private ProfileModel profileModel = ProfileModel.getInstance();



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

        // Save all information from text fields and create profile
        String name = txtFieldName.getText();
        double workingHours = Double.parseDouble(txtFieldWorkingHours.getText());
        double overhead = Double.parseDouble(txtFieldOverhead.getText());
        double utilization = Double.parseDouble(txtFieldUtilization.getText());
        double annualAmount = Double.parseDouble(txtFieldAnnualAmount.getText());
        double annualSalary = Double.parseDouble(txtFieldAnnualSalary.getText());
        String country = comboBoxCountry.getValue();
        Profile.ProfileType actualType = null;

        for (Profile.ProfileType type : Profile.ProfileType.values()) {
            if (type.getDisplayName().equals(comboBoxType.getValue())) {
                actualType = type;
                break;
            }
        }

        if (actualType == null) {
            System.err.println("Invalid profile type: " + comboBoxType.getValue());
        }

        Profile profile = new Profile(-1, name,annualSalary,workingHours,annualAmount,overhead,utilization,country,actualType);

        profileModel.createProfile(profile);


        // Close down window
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

}
