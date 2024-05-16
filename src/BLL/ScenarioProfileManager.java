package BLL;

import BE.Profile;
import DAL.ScenarioProfileDAO;

import java.util.List;

public class ScenarioProfileManager {
    private ScenarioProfileDAO scenarioProfileDAO;

    public ScenarioProfileManager() {
        this.scenarioProfileDAO = new ScenarioProfileDAO();
    }

    public List<Profile> getAllProfiles(int scenarioId){
        return scenarioProfileDAO.getAllProfiles(scenarioId);
    }

    public void createScenarioProfile(int scenarioId, int profileId) {
        scenarioProfileDAO.addToScenario(scenarioId, profileId);
    }

    public void deleteScenarioProfile(int scenarioId, int profileId) {
        scenarioProfileDAO.deleteFromScenario(scenarioId, profileId);
    }
}
