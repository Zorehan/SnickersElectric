package DAL;

import BE.Profile;
import BE.Scenario;
import BE.ScenarioProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScenarioProfileDAO implements GenericDAO<ScenarioProfile>{
    @Override
    public List<ScenarioProfile> getAll() {
        List<ScenarioProfile> allScenariosProfiles = new ArrayList<>();
        String sql = "SELECT * FROM dbo.ScenarioProfiles;";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int scenarioId = rs.getInt("scenarioId");
                int profileId = rs.getInt("profileId");


                ScenarioProfile scenarioProfile = new ScenarioProfile(scenarioId, profileId);
                allScenariosProfiles.add(scenarioProfile);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allScenariosProfiles;
    }

    @Override
    public ScenarioProfile create(ScenarioProfile scenarioProfile) {
        String sql = "INSERT INTO dbo.ScenarioProfiles (scenarioId, profileId) VALUES (?,?);";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, scenarioProfile.getScenarioId());
            stmt.setInt(2, scenarioProfile.getProfileId());

            stmt.executeUpdate();

            return scenarioProfile;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(ScenarioProfile scenarioProfile) {
        String sql = "DELETE FROM dbo.ScenarioProfiles WHERE scenarioId = ? AND profileId = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, scenarioProfile.getScenarioId());
            stmt.setInt(2, scenarioProfile.getProfileId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Tror ikke vi skal bruge denne? Kan ikke helt se et scenarie hvor man skal opdatere
     * kun delete og create.
     */
    @Override
    public ScenarioProfile update(ScenarioProfile entity) {
        return null;
    }
}
