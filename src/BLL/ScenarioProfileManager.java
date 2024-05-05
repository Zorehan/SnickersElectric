package BLL;

import BE.Scenario;
import BE.ScenarioProfile;
import DAL.ScenarioDAO;
import DAL.ScenarioProfileDAO;

import java.util.List;

public class ScenarioProfileManager {
    private ScenarioProfileDAO scenarioProfileDAO;

    public ScenarioProfileManager() {
        this.scenarioProfileDAO = new ScenarioProfileDAO();
    }

    public List<ScenarioProfile> getAllScenarioProfiles(){
        return scenarioProfileDAO.getAll();
    }

    public ScenarioProfile createScenarioProfile(ScenarioProfile scenarioProfile) {
        return scenarioProfileDAO.create(scenarioProfile);
    }

    public void deleteScenarioProfile(ScenarioProfile scenarioProfile) {
        scenarioProfileDAO.delete(scenarioProfile);
    }

    public ScenarioProfile updateScenarioProfile(ScenarioProfile scenarioProfile) {
        return scenarioProfileDAO.update(scenarioProfile);
    }
}
