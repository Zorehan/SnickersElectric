package BLL;

import BE.ScenarioProfile;
import DAL.ScenarioProfileDAO;
import util.Exception; // Importing the Exception class from the util package

import java.util.List;

public class ScenarioProfileManager {
    private final ScenarioProfileDAO scenarioProfileDAO;

    public ScenarioProfileManager() {
        this.scenarioProfileDAO = new ScenarioProfileDAO();
    }

    public List<ScenarioProfile> getAllScenarioProfiles() throws Exception {
        try {
            return scenarioProfileDAO.getAll();
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error getting all scenario profiles", ex);
        }
    }

    public ScenarioProfile createScenarioProfile(ScenarioProfile scenarioProfile) throws Exception {
        try {
            return scenarioProfileDAO.create(scenarioProfile);
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error creating scenario profile", ex);
        }
    }

    public void deleteScenarioProfile(ScenarioProfile scenarioProfile) throws Exception {
        try {
            scenarioProfileDAO.delete(scenarioProfile);
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error deleting scenario profile", ex);
        }
    }

    public ScenarioProfile updateScenarioProfile(ScenarioProfile scenarioProfile) throws Exception {
        try {
            return scenarioProfileDAO.update(scenarioProfile);
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error updating scenario profile", ex);
        }
    }
}
