package GUI.Controller.View;

import BE.Log;
import BE.Profile;
import GUI.Model.LogModel;
import GUI.Model.ProfileModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class logViewController implements Initializable {

    @FXML
    private TableView<Log> tblViewLogs;
    @FXML
    private TableColumn<Log, String> logTextColumn;
    @FXML
    private TableColumn<Log, String> nameColumn;

    private final ProfileModel profileModel = ProfileModel.getInstance();
    private final LogModel logModel = LogModel.getInstance();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
    }

    private void setupTable()
    {
        tblViewLogs.setItems(logModel.getAllLogs());
        logTextColumn.setCellValueFactory(new PropertyValueFactory<>("logText"));

        nameColumn.setCellValueFactory(cellData -> {
            Log log = cellData.getValue();
            Profile profile = profileModel.getProfileById(log.getReferenceProfileId());
            return new SimpleStringProperty(profile != null ? profile.getName() : "Unknown");
        });
    }
}
