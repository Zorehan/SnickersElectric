package DAL;

import BE.Profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScenarioProfileDAO{
    private Connection getConnection() {
        return new DatabaseConnector().getConnection();
    }
    public List<Profile> getAllProfiles(int scenarioId) {
        List<Profile> allScenariosProfiles = new ArrayList<>();
        String sql = "SELECT P.* FROM dbo.Profiles P INNER JOIN dbo.ScenarioProfiles SP ON P.id = SP.ProfileId WHERE SP.scenarioId = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, scenarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double annualSalary = rs.getDouble("annualSalary");
                double overheadPercent = rs.getDouble("overheadMultiplier");
                double annualAmount = rs.getDouble("annualAmount");
                double workHours = rs.getDouble("workHours");
                double utilPercent = rs.getDouble("utilizationPercentage");
                String country = rs.getString("country");
                Profile.ProfileType type = Profile.ProfileType.valueOf(rs.getString("type"));

                Profile profile = new Profile(id, name, annualSalary, workHours, annualAmount, overheadPercent, utilPercent, country, type);
                allScenariosProfiles.add(profile);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allScenariosProfiles;
    }

    public void addToScenario(int scenarioId, int profileId) {
        String sql = "INSERT INTO dbo.ScenarioProfiles (scenarioId, profileId) VALUES (?,?);";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, scenarioId);
            stmt.setInt(2, profileId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFromScenario(int scenarioId, int profileId) {
        String sql = "DELETE FROM dbo.ScenarioProfiles WHERE scenarioId = ? AND profileId = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, scenarioId);
            stmt.setInt(2, profileId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
