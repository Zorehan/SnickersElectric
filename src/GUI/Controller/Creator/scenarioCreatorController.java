package GUI.Controller.Creator;

import BE.Profile;
import BE.Scenario;
import BE.ScenarioProfile;
import GUI.Model.ProfileModel;
import GUI.Model.ScenarioModel;
import GUI.Model.ScenarioProfileModel;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class scenarioCreatorController implements Initializable {
    private ProfileModel profileModel = ProfileModel.getInstance();
    private ScenarioModel scenarioModel = ScenarioModel.getInstance();
    private ScenarioProfileModel scenarioProfileModel = ScenarioProfileModel.getInstance();
    @FXML
    private TableView<Profile> tblSelected, tblAvailable;
    @FXML
    private TableColumn<Scenario, String> colName, colCountry, colAvailName, colAvailCountry;
    @FXML
    private TableColumn<Scenario, Double> colHourlyRate, colDailyRate, colAvailHourlyRate, colAvailDailyRate;
    @FXML
    private Label lblProfileAmount, lblDailyRate, lblHourlyRate;
    @FXML
    private TextField txtName;
    @FXML
    private Button btnSave, btnCancel;
    private IntegerProperty profileAmount = new SimpleIntegerProperty(0);
    private StringProperty hourlyRate = new SimpleStringProperty("");
    private StringProperty dailyRate = new SimpleStringProperty("");
    private ObservableList<Profile> selectedProfiles;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblAvailable.setItems(profileModel.getObservableProfiles());
        colAvailName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAvailCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        colAvailHourlyRate.setCellValueFactory(new PropertyValueFactory<>("hourlyRate"));
        colAvailDailyRate.setCellValueFactory(new PropertyValueFactory<>("dailyRate"));

        selectedProfiles = FXCollections.observableArrayList();
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        colHourlyRate.setCellValueFactory(new PropertyValueFactory<>("hourlyRate"));
        colDailyRate.setCellValueFactory(new PropertyValueFactory<>("dailyRate"));

        BooleanProperty nameEntered = new SimpleBooleanProperty(false);
        BooleanProperty profilesAdded = new SimpleBooleanProperty(false);

        nameEntered.bind(Bindings.isNotEmpty(txtName.textProperty()));
        profilesAdded.bind(Bindings.isNotEmpty(tblSelected.getItems()));

        btnSave.disableProperty().bind(
                txtName.textProperty().isEmpty().or(Bindings.size(selectedProfiles).isEqualTo(0))
        );

        selectedProfiles.addListener((ListChangeListener<Profile>) change -> {
            updateLabels();
        });
        lblProfileAmount.textProperty().bind(profileAmount.asString());
        lblHourlyRate.textProperty().bind(hourlyRate);
        lblDailyRate.textProperty().bind(dailyRate);
    }
    @FXML
    private void clickAssign(ActionEvent actionEvent) {
        selectedProfiles.add(tblAvailable.getSelectionModel().getSelectedItem());
        tblSelected.setItems(selectedProfiles);
    }

    @FXML
    private void clickUnassign(ActionEvent actionEvent) {
        selectedProfiles.remove(tblSelected.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clickSave(ActionEvent actionEvent) {
        Scenario scenario = scenarioModel.createScenario(new Scenario(
                        -1, txtName.getText(),
                        Double.parseDouble(lblHourlyRate.getText()),
                        Double.parseDouble(lblDailyRate.getText())));

        for (Profile profile : selectedProfiles) {
            ScenarioProfile scenarioProfile = new ScenarioProfile(scenario.getId(), profile.getId());
            scenarioProfileModel.createScenario(scenarioProfile);
        }

        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

    private void updateLabels() {
        profileAmount.set(selectedProfiles.size());
        double totalHourlyRate = selectedProfiles.stream().mapToDouble(Profile::getHourlyRate).sum();
        double totalDailyRate = selectedProfiles.stream().mapToDouble(Profile::getDailyRate).sum();
        DecimalFormat df = new DecimalFormat("#.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        hourlyRate.set(df.format(totalHourlyRate));
        dailyRate.set(df.format(totalDailyRate));
    }
}
