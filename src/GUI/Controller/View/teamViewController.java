package GUI.Controller.View;

import BE.Team;
import GUI.Model.TeamModel;
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

import java.net.URL;
import java.util.ResourceBundle;

public class teamViewController implements Initializable {

    // FXML elements
    @FXML private Button btnTeamList;
    @FXML private TableView<Team> tblViewTeams;
    @FXML private TableColumn<Team, String> nameColumn;
    @FXML private TableColumn<Team, String> countryColumn;
    @FXML private TableColumn<Team, String> typeColumn;

    // Models
    private final TeamModel teamModel = TeamModel.getInstance();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableColumns();
        initTable();
        addRightClickFunctionality();

        // Add event handler for double-click on table rows
        tblViewTeams.setRowFactory(tv -> {
            TableRow<Team> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    openTeamList(); // Call the method to open TeamList
                }
            });
            return row;
        });
    }

    // Delete the selected team
    @FXML
    private void deleteSelectedTeam() {
        Team selectedTeam = tblViewTeams.getSelectionModel().getSelectedItem();
        if (selectedTeam != null) {
            // Show confirmation dialog
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Confirm Deletion");
            confirmation.setContentText("Are you sure you want to delete the selected team?");

            // Show the dialog and wait for user response
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // User confirmed deletion, proceed with deletion
                    teamModel.deleteTeam(selectedTeam);
                }
            });
        } else {
            // If no team is selected, show an error pop-up
            showErrorDialog("Please select a team to delete.");
        }
    }


    // Open the team editor window
    @FXML
    private void openTeamEditor() {
        Team selectedTeam = tblViewTeams.getSelectionModel().getSelectedItem();
        if (selectedTeam != null) {
            openWindow("../../View/teamEditor.fxml", null);
        } else {
            showErrorDialog("Please select a team to edit.");
        }

    }
    // Open the team creator window
    @FXML
    private void openTeamCreator() {
        openWindow("../../View/teamCreator.fxml", null);
    }

    // Open the profile adder window
    @FXML
    private void openProfileAdder() {
        openProfileWindow("../../View/profileAdder.fxml");
    }

    // Open the profile remover window
    @FXML
    private void openTeamList() {
        openProfileWindow("../../View/profileRemover.fxml");
    }

    // Open a window with a specified FXML file
    private void openProfileWindow(String fxmlPath) {
        Team selectedTeam = tblViewTeams.getSelectionModel().getSelectedItem();
        if (selectedTeam != null) {
            openWindow(fxmlPath, selectedTeam);
        } else {
            showErrorDialog("Please select a team first.");
        }
    }

    // Open a window with a specified FXML file and selected team
    private void openWindow(String fxmlPath, Team selectedTeam) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Object controller = loader.getController();

            // If the loaded controller is profileRemoverController, then send selectedTeam to profileRemoverController
            if (controller instanceof profileRemoverController) {
                ((profileRemoverController) controller).setSelectedTeam(selectedTeam);
            }

            // If the loaded controller is profileAdderController, then send selectedTeam to profileAdderController
            if (controller instanceof profileAdderController) {
                ((profileAdderController) controller).setSelectedTeam(selectedTeam);
            }

            // Configure the stage
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Schneider Electric");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Initialize table columns
    private void initTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().toString()));
    }

    // Initialize table items and selection listener
    private void initTable() {
        tblViewTeams.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                teamModel.setChosenTeam(newValue);
            }
        });
        tblViewTeams.setItems(teamModel.getObservableTeams());
    }

    // Show an error dialog with the specified message
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void addRightClickFunctionality() {
        ContextMenu contextMenu = new ContextMenu();

        // Define menu items
        MenuItem addPersonToTeam = new MenuItem("Add person to team");
        MenuItem viewTeams = new MenuItem("View teamlist");
        MenuItem editMenuItem = new MenuItem("Edit team");
        MenuItem deleteMenuItem = new MenuItem("Delete team");

        // Set actions for menu items
        editMenuItem.setOnAction(event -> openTeamEditor());
        deleteMenuItem.setOnAction(event -> deleteSelectedTeam());
        viewTeams.setOnAction(event -> openTeamList());
        addPersonToTeam.setOnAction(event -> openProfileAdder());

        // Add menu items to context menu
        contextMenu.getItems().addAll(addPersonToTeam,viewTeams,editMenuItem, deleteMenuItem);

        // Associate context menu with table view
        tblViewTeams.setContextMenu(contextMenu);
    }
}

