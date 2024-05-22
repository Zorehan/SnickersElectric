package GUI.Controller.Editor;

import BE.Log;
import BE.Profile;
import GUI.Model.LogModel;
import GUI.Model.ProfileModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;
/*
Controlleren her styrer popupvinduet der kommer op når man trykker "Edit Profile" i "Profiles" tabben i applikationen
vinduets formål er at redigere i en allerde eksisterende profil ved at ændre de forskellige værdier profilen har ved at benytte textfieldsne og comboboxes
ved tryk på "Save" knappen bliver saveInformation() metoden kaldt hvilket kalder updateProfile() metoden fra Modelklassen som opdaterer profilen i databasen.
 */
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

    private final ProfileModel profileModel = ProfileModel.getInstance();
    private final LogModel logModel = LogModel.getInstance();

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
        Locale.setDefault(Locale.ENGLISH);
        String[] countryCodes = Locale.getISOCountries();
        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            comboBoxCountry.getItems().add(locale.getDisplayCountry());
            if(locale.getDisplayCountry().equals(chosenProfile.getCountry())){
                comboBoxCountry.getSelectionModel().select(locale.getDisplayCountry());
            }
        }
    }

    private void initializeProfileTypes() {
        for (Profile.ProfileType type : Profile.ProfileType.values()) {
            comboBoxType.getItems().add(type.getDisplayName());

            if(type.equals(chosenProfile.getType())){
                comboBoxType.getSelectionModel().select(type.getDisplayName());
            }
        }
    }

    @FXML
    private void saveInformation() {

        Profile originalProfile = new Profile(0, chosenProfile.getName(), chosenProfile.getAnnualSalary(),chosenProfile.getWorkHours(), chosenProfile.getAnnualAmount(), chosenProfile.getOverheadPercent(), chosenProfile.getUtilizationPercent(), chosenProfile.getCountry(), chosenProfile.getType());


        chosenProfile.setName(txtFieldName.getText());
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

            logChanges(originalProfile,chosenProfile);

        } catch (Exception e )
        {
            e.printStackTrace();
        }

        // Close down window
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }


    private void logChanges(Profile originalProfile, Profile updatedProfile) {
        StringBuilder changes = new StringBuilder();
        changes.append("Profile: ").append(originalProfile.getName()).append(" was updated. Changes:");

        if (!originalProfile.getName().equals(updatedProfile.getName())) {
            changes.append("\nName: ").append(originalProfile.getName()).append(" -> ").append(updatedProfile.getName());
        }
        if (originalProfile.getWorkHours() != updatedProfile.getWorkHours()) {
            changes.append("\nWork Hours: ").append(originalProfile.getWorkHours()).append(" -> ").append(updatedProfile.getWorkHours());
        }
        if (originalProfile.getOverheadPercent() != updatedProfile.getOverheadPercent()) {
            changes.append("\nOverhead Percent: ").append(originalProfile.getOverheadPercent()).append(" -> ").append(updatedProfile.getOverheadPercent());
        }
        if (originalProfile.getUtilizationPercent() != updatedProfile.getUtilizationPercent()) {
            changes.append("\nUtilization Percent: ").append(originalProfile.getUtilizationPercent()).append(" -> ").append(updatedProfile.getUtilizationPercent());
        }
        if (originalProfile.getAnnualAmount() != updatedProfile.getAnnualAmount()) {
            changes.append("\nAnnual Amount: ").append(originalProfile.getAnnualAmount()).append(" -> ").append(updatedProfile.getAnnualAmount());
        }
        if (originalProfile.getAnnualSalary() != updatedProfile.getAnnualSalary()) {
            changes.append("\nAnnual Salary: ").append(originalProfile.getAnnualSalary()).append(" -> ").append(updatedProfile.getAnnualSalary());
        }
        if (!originalProfile.getCountry().equals(updatedProfile.getCountry())) {
            changes.append("\nCountry: ").append(originalProfile.getCountry()).append(" -> ").append(updatedProfile.getCountry());
        }
        if (!originalProfile.getType().equals(updatedProfile.getType())) {
            changes.append("\nType: ").append(originalProfile.getType().getDisplayName()).append(" -> ").append(updatedProfile.getType().getDisplayName());
        }

        Log log = new Log(-1, updatedProfile.getName(), changes.toString() + " at: " + Date.valueOf(LocalDate.now()));
        try {
            logModel.createLog(log);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
