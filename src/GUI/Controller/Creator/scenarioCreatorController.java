package GUI.Controller.Creator;

import BE.Profile;
import BE.Scenario;
import GUI.Model.ProfileModel;
import GUI.Model.ScenarioModel;
import GUI.Model.ScenarioProfileModel;
import util.Calculator;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    private Label lblProfileAmount, lblDailyRate, lblHourlyRate, lblMarginDailyRate, lblMarginHourlyRate, lblMarkupDailyRate, lblMarkupHourlyRate;
    @FXML
    private TextField txtName;
    @FXML
    private Spinner<Double> spinMargin, spinMarkup, spinWorkHours;
    @FXML
    private Button btnSave, btnCancel;
    private IntegerProperty profileAmount = new SimpleIntegerProperty(0);
    private StringProperty hourlyRate = new SimpleStringProperty("");
    private StringProperty dailyRate = new SimpleStringProperty("");
    private StringProperty markupDailyRate = new SimpleStringProperty("");
    private StringProperty markupHourlyRate = new SimpleStringProperty("");
    private StringProperty marginDailyRate = new SimpleStringProperty("");
    private StringProperty marginHourlyRate = new SimpleStringProperty("");
    private ObservableList<Profile> selectedProfiles;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spinMargin.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 100.0, 0.0, 0.5));
        spinMarkup.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 100.0, 0.0, 0.5));
        spinWorkHours.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 100.0, 0.0, 0.5));

        initTables();
        initBindings();
    }

    private void initTables() {
        // Initialize the available profiles
        tblAvailable.setItems(profileModel.getObservableProfiles());
        colAvailName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAvailCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        colAvailHourlyRate.setCellValueFactory(new PropertyValueFactory<>("hourlyRate"));
        colAvailDailyRate.setCellValueFactory(new PropertyValueFactory<>("dailyRate"));

        // Initialize the selected profiles.
        selectedProfiles = FXCollections.observableArrayList();
        tblSelected.setItems(selectedProfiles);
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        colHourlyRate.setCellValueFactory(new PropertyValueFactory<>("hourlyRate"));
        colDailyRate.setCellValueFactory(new PropertyValueFactory<>("dailyRate"));
    }

    private void initBindings() {
        //Assign and bind  boolean properties to nameEntered and Profiles added, to ensure these are filled before you can save.
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

        spinMargin.valueProperty().addListener((observable, oldValue, newValue) -> updateLabels());
        spinMarkup.valueProperty().addListener((observable, oldValue, newValue) -> updateLabels());
        spinWorkHours.valueProperty().addListener((observable, oldValue, newValue) -> updateLabels());

        // Bind the relevant labels.
        lblProfileAmount.textProperty().bind(profileAmount.asString());
        lblHourlyRate.textProperty().bind(hourlyRate);
        lblDailyRate.textProperty().bind(dailyRate);
        lblMarginDailyRate.textProperty().bind(marginDailyRate);
        lblMarginHourlyRate.textProperty().bind(marginHourlyRate);
        lblMarkupDailyRate.textProperty().bind(markupDailyRate);
        lblMarkupHourlyRate.textProperty().bind(markupHourlyRate);
    }
    @FXML
    private void clickAssign(ActionEvent actionEvent) {
        Profile selectedProfile = tblAvailable.getSelectionModel().getSelectedItem();

        selectedProfiles.add(selectedProfile);
        tblAvailable.getItems().remove(selectedProfile);
    }

    @FXML
    private void clickUnassign(ActionEvent actionEvent) {
        Profile selectedProfile = tblSelected.getSelectionModel().getSelectedItem();

        selectedProfiles.remove(selectedProfile);
        tblAvailable.getItems().add(selectedProfile);
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
                        Double.parseDouble(lblDailyRate.getText()),
                        spinMargin.getValue(),
                        spinMarkup.getValue(),
                        spinWorkHours.getValue()));

        for (Profile profile : selectedProfiles) {
            scenarioProfileModel.addToScenario(scenario.getId(), profile.getId());
        }

        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

    private void updateLabels() {
        for (Profile profile : selectedProfiles) {
            profile.setDailyRate(spinWorkHours.getValue());
        }

        profileAmount.set(selectedProfiles.size());
        double totalHourlyRate = selectedProfiles.stream().mapToDouble(Profile::getHourlyRate).sum();
        double totalDailyRate = selectedProfiles.stream().mapToDouble(Profile::getDailyRate).sum();

        double margin = spinMargin.getValue();
        double markup = spinMarkup.getValue();

        DecimalFormat df = new DecimalFormat("#.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        hourlyRate.set(df.format(totalHourlyRate));
        dailyRate.set(df.format(totalDailyRate));
        markupHourlyRate.set(df.format(Calculator.calcMarkup(totalHourlyRate, markup)));
        markupDailyRate.set(df.format(Calculator.calcMarkup(totalDailyRate, markup)));
        marginHourlyRate.set(df.format(Calculator.calcGrossMargin(totalHourlyRate, margin)));
        marginDailyRate.set(df.format(Calculator.calcGrossMargin(totalDailyRate, margin)));
    }
}
