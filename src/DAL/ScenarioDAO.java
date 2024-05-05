package DAL;

import BE.Profile;
import BE.Scenario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDAO implements GenericDAO<Scenario> {

    @Override
    public List<Scenario> getAll() {
        List<Scenario> allScenarios = new ArrayList<>();
        String sql = "SELECT * FROM dbo.Scenarios;";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double hourlyRate = rs.getDouble("hourlyRate");
                double dailyRate = rs.getDouble("dailyRate");

                Scenario scenario = new Scenario(id, name, hourlyRate, dailyRate);
                allScenarios.add(scenario);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allScenarios;
    }

    @Override
    public Scenario create(Scenario scenario) {
        String sql = "INSERT INTO dbo.Scenarios (name, hourlyRate, dailyRate) VALUES (?,?,?);";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, scenario.getName());
            stmt.setDouble(2, scenario.getHourlyRate());
            stmt.setDouble(3, scenario.getDailyRate());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    scenario.setId(rs.getInt(1));
                }
            }

            return scenario;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Scenario scenario) {
        String sql = "DELETE FROM dbo.Scenarios WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, scenario.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Scenario update(Scenario scenario) {
        String sql = "UPDATE dbo.Scenarios SET name = ?, hourlyRate = ?, dailyRate = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, scenario.getName());
            stmt.setDouble(2, scenario.getHourlyRate());
            stmt.setDouble(3, scenario.getDailyRate());
            stmt.setInt(4, scenario.getId());

            stmt.executeUpdate();
            return scenario;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
