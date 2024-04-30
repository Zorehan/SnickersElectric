package DAL;

import BE.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO implements GenericDAO<Team> {
    @Override
    public List<Team> getAll() {
        List<Team> allTeams = new ArrayList<>();
        String sql = "SELECT * FROM dbo.Teams;";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String country = rs.getString("country");
                Team.TeamType type = Team.TeamType.valueOf(rs.getString("type"));

                Team team = new Team(id, name, country, type);
                allTeams.add(team);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allTeams;
    }

    @Override
    public Team create(Team team) {
        String sql = "INSERT INTO dbo.Teams (name, country, type) VALUES (?,?,?);";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, team.getName());
            stmt.setString(2, team.getCountry());
            stmt.setString(3, team.getType().toString());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    team.setId(rs.getInt(1));
                }
            }

            return team;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Team team) {
        String sql = "DELETE FROM dbo.Teams WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, team.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Team update(Team team) {
        String sql = "UPDATE dbo.Teams SET name = ?, country = ?, type = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, team.getName());
            stmt.setString(2, team.getCountry());
            stmt.setString(3, team.getType().toString());
            stmt.setInt(4, team.getId());

            stmt.executeUpdate();
            return team;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
