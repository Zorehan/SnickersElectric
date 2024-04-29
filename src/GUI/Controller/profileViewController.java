package GUI.Controller;

import BE.Profile;
import GUI.Model.ProfileModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class profileViewController implements Initializable {

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
            // If no profile is selected, show an error pop-up
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a profile to delete.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Det her burde meget gerne virke ligeså snart vi har en modelklasse knyttet til og noget crud

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
    }
}
