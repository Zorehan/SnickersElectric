package DAL;

import BE.Profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfileDAO implements GenericDAO<Profile> {
    @Override
    public List<Profile> getAll() {
        List<Profile> allProfiles = new ArrayList<>();
        String sql = "SELECT * FROM dbo.Profiles;";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
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
                allProfiles.add(profile);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allProfiles;
    }

    @Override
    public Profile create(Profile profile) {
        String sql = "INSERT INTO dbo.Profiles (name, annualSalary, overheadMultiplier, annualAmount, workHours, utilizationPercentage, country, type) VALUES (?,?,?,?,?,?,?,?);";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, profile.getName());
            stmt.setDouble(2, profile.getAnnualSalary());
            stmt.setDouble(3, profile.getOverheadPercent());
            stmt.setDouble(4, profile.getAnnualAmount());
            stmt.setDouble(5, profile.getWorkHours());
            stmt.setDouble(6, profile.getUtilizationPercent());
            stmt.setString(7, profile.getCountry());
            stmt.setString(8, profile.getType().toString());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    profile.setId(rs.getInt(1));
                }
            }

            return profile;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Profile profile) {
        String sql1 = "DELETE FROM dbo.TeamProfiles WHERE ProfileId = ?";
        String sql2 = "DELETE FROM dbo.ScenarioProfiles WHERE profileId = ?";
        String sql3 = "DELETE FROM dbo.Profiles WHERE id = ?";


        try (Connection conn = getConnection();
             PreparedStatement stmt1 = conn.prepareStatement(sql1)) {
             PreparedStatement stmt2 = conn.prepareStatement(sql2);
            PreparedStatement stmt3 = conn.prepareStatement(sql3);

            stmt1.setInt(1, profile.getId());
            stmt2.setInt(1, profile.getId());
            stmt3.setInt(1, profile.getId());

            stmt1.executeUpdate();
            stmt2.executeUpdate();
            stmt3.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Profile update(Profile profile) {
        String sql = "UPDATE dbo.Profiles SET name = ?, annualSalary = ?, overheadMultiplier = ?, annualAmount = ?, workHours = ?, utilizationPercentage = ?, country = ?, type = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, profile.getName());
            stmt.setDouble(2, profile.getAnnualSalary());
            stmt.setDouble(3, profile.getOverheadPercent());
            stmt.setDouble(4, profile.getAnnualAmount());
            stmt.setDouble(5, profile.getWorkHours());
            stmt.setDouble(6, profile.getUtilizationPercent());
            stmt.setString(7, profile.getCountry());
            stmt.setString(8, profile.getType().toString());
            stmt.setInt(9, profile.getId());

            stmt.executeUpdate();
            return profile;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
