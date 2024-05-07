package DAL;

import BE.Profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfileTeamDAO {
    private Connection getConnection() {
        return new DatabaseConnector().getConnection();
    }

    public List<Profile> getProfilesForTeam(int teamId) {
        List<Profile> profiles = new ArrayList<>();
        String sql = "SELECT P.* FROM dbo.Profiles P INNER JOIN dbo.TeamProfiles TP ON P.id = TP.ProfileId WHERE TP.TeamId = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
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
                profiles.add(profile);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return profiles;
    }

    public void addProfileToTeam(int profileId, int teamId) {
        String sql = "INSERT INTO dbo.TeamProfiles (TeamId, ProfileId) VALUES (?, ?);";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, profileId);
            stmt.setInt(2, teamId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeProfileFromTeam(int profileId, int teamId) {
        String sql = "DELETE FROM dbo.TeamProfiles WHERE ProfileId = ? AND TeamId = ?;";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, profileId);
            stmt.setInt(2, teamId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}