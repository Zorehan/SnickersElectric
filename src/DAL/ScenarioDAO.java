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
                double margin = rs.getDouble("grossMargin");
                double markup = rs.getDouble("markup");
                double workHours = rs.getDouble("workHours");

                Scenario scenario = new Scenario(id, name, hourlyRate, dailyRate, margin, markup, workHours);
                allScenarios.add(scenario);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allScenarios;
    }

    @Override
    public Scenario create(Scenario scenario) {
        String sql = "INSERT INTO dbo.Scenarios (name, hourlyRate, dailyRate, grossMargin, markup, workHours) VALUES (?,?,?,?,?,?);";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, scenario.getName());
            stmt.setDouble(2, scenario.getHourlyRate());
            stmt.setDouble(3, scenario.getDailyRate());
            stmt.setDouble(4, scenario.getGrossMargin());
            stmt.setDouble(5, scenario.getMarkup());
            stmt.setDouble(6, scenario.getWorkHours());

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
        String sql1 = "DELETE FROM dbo.ScenarioProfiles WHERE scenarioId = ?";
        String sql2 = "DELETE FROM dbo.Scenarios WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt1 = conn.prepareStatement(sql1)) {
            PreparedStatement stmt2 = conn.prepareStatement(sql2);
            stmt1.setInt(1, scenario.getId());
            stmt2.setInt(1, scenario.getId());
            stmt1.executeUpdate();
            stmt2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Scenario update(Scenario scenario) {
        String sql = "UPDATE dbo.Scenarios SET name = ?, hourlyRate = ?, dailyRate = ?, grossMargin = ?, markup = ?, workHours = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, scenario.getName());
            stmt.setDouble(2, scenario.getHourlyRate());
            stmt.setDouble(3, scenario.getDailyRate());
            stmt.setDouble(4, scenario.getGrossMargin());
            stmt.setDouble(5, scenario.getMarkup());
            stmt.setDouble(6, scenario.getWorkHours());
            stmt.setInt(7, scenario.getId());

            stmt.executeUpdate();
            return scenario;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
