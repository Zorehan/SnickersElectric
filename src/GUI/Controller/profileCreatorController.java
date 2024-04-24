package GUI.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class profileCreatorController {

    @FXML
    private Button btnSave;

    @FXML
    private void saveInformation() {
        // TODO: Add saving data method

        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }


}
