package GUI.Controller;

import BE.Profile;
import GUI.Model.ProfileModel;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.SearchEngine;

import java.net.URL;
import java.util.ResourceBundle;

public class profileViewController implements Initializable {

    @FXML
    private TextField txtSearchField;

    private SearchEngine searchEngine;

    @FXML
    private TextField txtWorkHours;

    @FXML
    private TextField txtGM;

    @FXML
    private TextField txtMarkup;

    @FXML
    private TableView<Profile> tblViewProfiles;

    @FXML
    private TableColumn<Profile,String> nameColumn;

    @FXML
    private TableColumn<Profile, String> countryColumn;

    @FXML
    private TableColumn<Profile, Double> annualSalaryColumn;

    @FXML
    private TableColumn<Profile, Double> fixedAnnualAmountColumn;

    @FXML
    private TableColumn<Profile, Double> annualWorkingHoursColumn;

    @FXML
    private TableColumn<Profile, Integer> overheadColumn;

    @FXML
    private TableColumn<Profile, Double> utilizationColumn;

    @FXML
    private TableColumn<Profile, String> typeColumn;

    @FXML
    private TableColumn<Profile, Double> hourlyRateColumn;

    @FXML
    private TableColumn<Profile, Double> dailyRateColumn;

    private double gmMultiplier = 1.0;
    private double markupMultiplier = 1.0;


    private final ProfileModel profileModel = ProfileModel.getInstance();


    @FXML
    private void openProfileEditor() {
        openNewWindow("../View/profileEditor.fxml");
    }

    @FXML
    private void openProfileCreator() {
        openNewWindow("../View/profileCreator.fxml");
    }

    @FXML
    private void openNewWindow(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Schneider Electric");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteSelectedProfile() {
        Profile selectedProfile = tblViewProfiles.getSelectionModel().getSelectedItem();
        if (selectedProfile != null) {
            profileModel.deleteProfile(selectedProfile);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a profile to delete.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        searchEngine = new SearchEngine(profileModel.getObservableProfiles());
        txtSearchField.setPromptText("Type query here, split with ','");
        tblViewProfiles.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> profileModel.setChosenProfile(newValue));

        tblViewProfiles.setItems(profileModel.getObservableProfiles());
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


        txtSearchField.textProperty().addListener((observable, oldValue, newValue) -> searchEngine.filter(newValue));
        tblViewProfiles.setItems(searchEngine.getFilteredProfiles());

        DoubleBinding gmMultiplierBinding = createMultiplierBinding(txtGM);
        DoubleBinding markupMultiplierBinding = createMultiplierBinding(txtMarkup);

        DoubleBinding combinedMultiplierBinding = gmMultiplierBinding.multiply(markupMultiplierBinding);

        setupMultiplierCalculations(combinedMultiplierBinding, hourlyRateColumn, dailyRateColumn);

        // Den her del sørger for at de ændringer der kommer i profilesne direkte (altså setters kaldt direkte på BE's opdaterer dem med det samme i Observablelisten også
        profileModel.getObservableProfiles().forEach(profile -> {
            profile.addPropertyChangeListener(evt -> {
                if ("dailyRate".equals(evt.getPropertyName())) {
                    int index = profileModel.getObservableProfiles().indexOf(profile);
                    profileModel.getObservableProfiles().set(index, profile);
                }
            });
        });
        //Listener for tekstfielden der leder efter workhours
        txtWorkHours.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int hours;
                if (!newValue.isEmpty()) {
                    hours = Integer.parseInt(newValue);
                } else {
                    hours = 8;
                }
                if (hours >= 0) {

                    profileModel.getObservableProfiles().forEach(profile -> {
                        profile.setDailyRate(hours);
                    });
                } else {
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });

    }

    private DoubleBinding createMultiplierBinding(TextField textField) {
        return Bindings.createDoubleBinding(() -> {
            try {
                return 1.0 + (Double.parseDouble(textField.getText()) / 100.0);
            } catch (NumberFormatException e) {
                return 1.0;
            }
        }, textField.textProperty());
    }

    private void setupMultiplierCalculations(DoubleBinding multiplierBinding, TableColumn<Profile, Double> hourlyRateColumn, TableColumn<Profile, Double> dailyRateColumn) {

        hourlyRateColumn.setCellValueFactory(cellData ->
                Bindings.createObjectBinding(() -> {
                    double rate = cellData.getValue().getHourlyRate() * multiplierBinding.get();
                    return rate;
                }, multiplierBinding));

        dailyRateColumn.setCellValueFactory(cellData ->
                Bindings.createObjectBinding(() -> {
                    double rate = cellData.getValue().getDailyRate() * multiplierBinding.get();
                    return rate;
                }, multiplierBinding));
    }
}
