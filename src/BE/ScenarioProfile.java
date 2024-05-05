package BE;

public class ScenarioProfile {
    private int scenarioId;
    private int profileId;

    public ScenarioProfile(int scenarioId, int profileId) {
        this.scenarioId = scenarioId;
        this.profileId = profileId;
    }

    public int getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(int scenarioId) {
        this.scenarioId = scenarioId;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }
}
