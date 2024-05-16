package BLL;

import BE.Scenario;
import DAL.ScenarioDAO;
import util.Exception; // Importing the Exception class from the util package

import java.util.List;

public class ScenarioManager {
    private final ScenarioDAO scenarioDAO;

    public ScenarioManager() {
        this.scenarioDAO = new ScenarioDAO();
    }

    public List<Scenario> getAllScenarios() throws Exception {
        try {
            return scenarioDAO.getAll();
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error getting all scenarios", ex);
        }
    }

    public Scenario createScenario(Scenario scenario) throws Exception {
        try {
            return scenarioDAO.create(scenario);
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error creating scenario", ex);
        }
    }

    public void deleteScenario(Scenario scenario) throws Exception {
        try {
            scenarioDAO.delete(scenario);
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error deleting scenario", ex);
        }
    }

    public Scenario updateScenario(Scenario scenario) throws Exception {
        try {
            return scenarioDAO.update(scenario);
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error updating scenario", ex);
        }
    }
}
