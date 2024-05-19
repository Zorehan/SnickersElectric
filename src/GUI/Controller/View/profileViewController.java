package GUI.Controller.View;

import BE.Log;
import BE.Profile;
import GUI.Model.LogModel;
import GUI.Model.ProfileModel;
import GUI.Model.ProfileTeamModel;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.Exception;
import util.SearchEngine;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class profileViewController implements Initializable {

    // FXML elements
    @FXML private TextField txtSearchField;
    @FXML private TableView<Profile> tblViewProfiles;
    @FXML private TableColumn<Profile, String> nameColumn;
    @FXML private TableColumn<Profile, String> countryColumn;
    @FXML private TableColumn<Profile, Double> annualSalaryColumn;
    @FXML private TableColumn<Profile, Double> fixedAnnualAmountColumn;
    @FXML private TableColumn<Profile, Double> annualWorkingHoursColumn;
    @FXML private TableColumn<Profile, Integer> overheadColumn;
    @FXML private TableColumn<Profile, Double> utilizationColumn;
    @FXML private TableColumn<Profile, String> typeColumn;
    @FXML private TableColumn<Profile, Double> hourlyRateColumn;
    @FXML private TableColumn<Profile, Double> dailyRateColumn;

    // Model
    private final ProfileModel profileModel = ProfileModel.getInstance();
    private final SearchEngine searchEngine = new SearchEngine(profileModel.getObservableProfiles());
    private final ProfileTeamModel profileTeamModel = ProfileTeamModel.getInstance();
    private final LogModel logModel = LogModel.getInstance();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        setupSearch();
        setupListeners();
        addRightClickFunctionality();
    }

    // Setup table view
    private void setupTable() {
        tblViewProfiles.setItems(searchEngine.getFilteredProfiles());
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

    // Setup search functionality
    private void setupSearch() {
        txtSearchField.setPromptText("Type query here, split with ','");
        txtSearchField.textProperty().addListener((observable, oldValue, newValue) -> searchEngine.filter(newValue));
    }


    // Setup listeners for text fields
    private void setupListeners() {
        tblViewProfiles.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> profileModel.setChosenProfile(newValue));

        // Den her del sørger for at de ændringer der kommer i profilerne direkte (altså setters kaldt direkte på BE's opdaterer dem med det samme i Observablelisten også
        profileModel.getObservableProfiles().forEach(profile -> {
            profile.addPropertyChangeListener(evt -> {
                if ("dailyRate".equals(evt.getPropertyName())) {
                    int index = profileModel.getObservableProfiles().indexOf(profile);
                    profileModel.getObservableProfiles().set(index, profile);
                }
            });
        });
    }


    // Open profile editor window
    @FXML private void openProfileEditor() {
        Profile selectedProfile = tblViewProfiles.getSelectionModel().getSelectedItem();

        if (selectedProfile != null) {
            openNewWindow("../../View/profileEditor.fxml");
        } else {
            showAndLogError(new Exception("Please select a profile to edit."));
        }
    }

    // Open profile creator window
    @FXML private void openProfileCreator() {
        openNewWindow("../../View/profileCreator.fxml");
    }

    // Open a new window with specified FXML path
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
            showAndLogError(new Exception("Error opening new window."));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Delete selected profile
    @FXML
    private void deleteSelectedProfile() {
        Profile selectedProfile = tblViewProfiles.getSelectionModel().getSelectedItem();
        if (selectedProfile != null) {
            // Show confirmation dialog
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Confirm Deletion");
            confirmation.setContentText("Are you sure you want to delete the selected profile?");

            // Show the dialog and wait for user response
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // User confirmed deletion, proceed with deletion
                    try {
                        Log log = new Log(-1, selectedProfile.getId(), "Profile: " + selectedProfile.getName() + " was deleted at " +Date.valueOf(LocalDate.now()));
                        profileModel.deleteProfile(selectedProfile);
                        logModel.createLog(log);
                    } catch (Exception ex) {
                        // Handle the exception using showAndLogError method
                        showAndLogError(ex);
                    } catch (java.lang.Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } else {
            showAndLogError(new Exception("Please select a profile to delete."));
        }
    }

    private void addRightClickFunctionality() {
        ContextMenu contextMenu = new ContextMenu();

        // Define menu items
        MenuItem editMenuItem = new MenuItem("Edit profile");
        MenuItem deleteMenuItem = new MenuItem("Delete profile");

        // Set actions for menu items
        editMenuItem.setOnAction(event -> openProfileEditor());
        deleteMenuItem.setOnAction(event -> deleteSelectedProfile());

        // Add menu items to context menu
        contextMenu.getItems().addAll(editMenuItem, deleteMenuItem);

        // Associate context menu with table view
        tblViewProfiles.setContextMenu(contextMenu);
    }

    // Method for showing and logging error
    private static void showAndLogError(Exception ex) {
        Logger.getLogger(profileViewController.class.getName()).log(Level.SEVERE, null, ex);

        Alert alert = new Alert(Alert.AlertType.ERROR,
                ex.getMessage()
                        + String.format("%n")
                        + "See error log for technical details."
        );
        alert.showAndWait();
    }
}
