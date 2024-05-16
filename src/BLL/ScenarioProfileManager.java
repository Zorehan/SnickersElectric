package BLL;

import BE.Profile;
import DAL.ScenarioProfileDAO;
import util.Exception;

import java.util.List;

public class ScenarioProfileManager {
    private ScenarioProfileDAO scenarioProfileDAO;

    public ScenarioProfileManager() {
        this.scenarioProfileDAO = new ScenarioProfileDAO();
    }

    public List<Profile> getAllProfiles(int scenarioId){
        try{
            return scenarioProfileDAO.getAllProfiles(scenarioId);
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error getting all scenario profiles", ex);
        }
    }

    public void createScenarioProfile(int scenarioId, int profileId) {
        try{
            scenarioProfileDAO.addToScenario(scenarioId, profileId);
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error creating scenario profile", ex);
        }
    }

    public void deleteScenarioProfile(int scenarioId, int profileId) {
        try{
            scenarioProfileDAO.deleteFromScenario(scenarioId, profileId);
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error deleting scenario profile", ex);
        }
    }
}
