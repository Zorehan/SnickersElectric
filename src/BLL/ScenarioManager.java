package BLL;

import BE.Scenario;
import DAL.ScenarioDAO;

import java.util.List;

public class ScenarioManager {
    private ScenarioDAO scenarioDAO;

    public ScenarioManager() {
        this.scenarioDAO = new ScenarioDAO();
    }

    public List<Scenario> getAllScenarios(){
        return scenarioDAO.getAll();
    }

    public Scenario createScenario(Scenario scenario) {
        return scenarioDAO.create(scenario);
    }

    public void deleteScenario(Scenario scenario) {
        scenarioDAO.delete(scenario);
    }

    public Scenario updateScenario(Scenario scenario) {
        return scenarioDAO.update(scenario);
    }
}
