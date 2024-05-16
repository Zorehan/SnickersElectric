package GUI.Model;

import BE.Scenario;
import BLL.ScenarioManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ScenarioModel {
    private static ScenarioModel instance;
    private ScenarioManager scenarioManager;
    private ObservableList<Scenario> allScenarios;
    private Scenario scenario;

    private ScenarioModel() {
        this.scenarioManager = new ScenarioManager();

        allScenarios = FXCollections.observableArrayList();
        allScenarios.addAll(scenarioManager.getAllScenarios());
    }

    public static ScenarioModel getInstance() {
        if(instance == null) {
            instance = new ScenarioModel();
        }
        return instance;
    }

    public ObservableList<Scenario> getObservableScenarios(){
        return allScenarios;
    }

    public Scenario createScenario(Scenario newScenario) {
        Scenario scenario = scenarioManager.createScenario(newScenario);
        allScenarios.add(scenario);
        return scenario;
    }

    public void deleteScenario(Scenario scenario) {
        scenarioManager.deleteScenario(scenario);
        allScenarios.remove(scenario);
    }

    public void updateScenario(Scenario scenario) {
        scenarioManager.updateScenario(scenario);
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public Scenario getScenario() {
        return scenario;
    }
}
