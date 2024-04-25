package DAL;

import BE.Profile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfileDAO {
    private DatabaseConnector databaseConnector;

    public ProfileDAO() {
        databaseConnector = new DatabaseConnector();
    }
    public List<Profile> getAllProfiles() {
        List<Profile> allProfiles = new ArrayList<>();
        String sql = "SELECT * FROM dbo.Profiles;";
        try(Connection conn = databaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
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

    public Profile createProfile(Profile profile) {
        String sql = "INSERT INTO dbo.Profiles (name, annualSalary, overheadMultiplier, annualAmount, workHours, utilizationPercentage, country, type) VALUES (?,?,?,?,?,?,?,?);";
        try(Connection conn = databaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            stmt.setString(1, profile.getName());
            stmt.setDouble(2, profile.getAnnualSalary());
            stmt.setDouble(3, profile.getOverheadPercent());
            stmt.setDouble(4, profile.getAnnualAmount());
            stmt.setDouble(5, profile.getWorkHours());
            stmt.setDouble(6, profile.getUtilizationPercent());
            stmt.setString(7, profile.getCountry());
            stmt.setString(8, profile.getType().toString());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if(rs.next()) {
                id = rs.getInt(1);
            }

            Profile createdProfile = new Profile(id, profile.getName(), profile.getAnnualSalary(), profile.getWorkHours(), profile.getAnnualAmount(), profile.getOverheadPercent(), profile.getUtilizationPercent(), profile.getCountry(), profile.getType());
            return createdProfile;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteProfile(Profile profile) {
        String sql = "DELETE FROM dbo.Profiles WHERE id = ?;";
        try(Connection conn = databaseConnector.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, profile.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProfile(Profile profile) {
        String sql = "UPDATE dbo.Profiles SET name = ?, annualSalary = ?, overheadPercent = ?, annualAmount = ?, workHours = ?, utilPercent = ?, country = ?, type = ? WHERE id = ?;";
        try(Connection conn = databaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            stmt.setString(1, profile.getName());
            stmt.setDouble(2, profile.getAnnualSalary());
            stmt.setDouble(3, profile.getOverheadPercent());
            stmt.setDouble(4, profile.getAnnualAmount());
            stmt.setDouble(5, profile.getWorkHours());
            stmt.setDouble(6, profile.getUtilizationPercent());
            stmt.setString(7, profile.getCountry());
            stmt.setString(8, profile.getType().toString());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
