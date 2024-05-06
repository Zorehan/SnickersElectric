package DAL;

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

    public List<Integer> getTeamsForProfile(int profileId) {
        List<Integer> teamIds = new ArrayList<>();
        String sql = "SELECT TeamId FROM dbo.TeamProfiles WHERE ProfileId = ?;";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, profileId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                teamIds.add(rs.getInt("TeamId"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teamIds;
    }

    public List<Integer> getProfilesForTeam(int teamId) {
        List<Integer> profileIds = new ArrayList<>();
        String sql = "SELECT ProfileId FROM dbo.TeamProfiles WHERE TeamId = ?;";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                profileIds.add(rs.getInt("ProfileId"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return profileIds;
    }
}
