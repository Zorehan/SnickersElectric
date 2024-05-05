package GUI.Model;

import BE.ScenarioProfile;
import BLL.ScenarioProfileManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ScenarioProfileModel {
    private static ScenarioProfileModel instance;
    private ScenarioProfileManager scenarioProfileManager;
    private ObservableList<ScenarioProfile> allScenarioProfiles;

    private ScenarioProfileModel() {
        this.scenarioProfileManager = new ScenarioProfileManager();

        allScenarioProfiles = FXCollections.observableArrayList();
        allScenarioProfiles.addAll(scenarioProfileManager.getAllScenarioProfiles());
    }

    public static ScenarioProfileModel getInstance() {
        if(instance == null) {
            instance = new ScenarioProfileModel();
        }
        return instance;
    }

    public ObservableList<ScenarioProfile> getObservableScenarios(){
        return allScenarioProfiles;
    }

    public ScenarioProfile createScenario(ScenarioProfile newScenarioProfile) {
        ScenarioProfile scenario = scenarioProfileManager.createScenarioProfile(newScenarioProfile);
        allScenarioProfiles.add(scenario);
        return scenario;
    }

    public void deleteScenario(ScenarioProfile newScenarioProfile) {
        scenarioProfileManager.deleteScenarioProfile(newScenarioProfile);
        allScenarioProfiles.remove(newScenarioProfile);
    }

    public void updateScenario(ScenarioProfile scenarioProfile) {
        ScenarioProfile newScenarioProfile = scenarioProfileManager.updateScenarioProfile(scenarioProfile);
        allScenarioProfiles.remove(scenarioProfile);
        allScenarioProfiles.add(newScenarioProfile);
    }
}
