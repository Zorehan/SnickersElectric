package GUI.Model;

import BE.Profile;
import BLL.ScenarioProfileManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ScenarioProfileModel {
    private static ScenarioProfileModel instance;
    private ScenarioProfileManager scenarioProfileManager;
    private ObservableList<Profile> allScenarioProfiles;

    private ScenarioProfileModel() {
        this.scenarioProfileManager = new ScenarioProfileManager();
    }

    public static ScenarioProfileModel getInstance() {
        if(instance == null) {
            instance = new ScenarioProfileModel();
        }
        return instance;
    }

    public ObservableList<Profile> getAllObservableProfiles(int scenarioId){
        allScenarioProfiles = FXCollections.observableArrayList();
        allScenarioProfiles.setAll(scenarioProfileManager.getAllProfiles(scenarioId));
        return allScenarioProfiles;
    }

    public void addToScenario(int scenarioId, int profileId) {
        scenarioProfileManager.createScenarioProfile(scenarioId, profileId);
    }

    public void deleteFromScenario(int scenarioId, int profileId) {
        scenarioProfileManager.deleteScenarioProfile(scenarioId, profileId);
    }
}
