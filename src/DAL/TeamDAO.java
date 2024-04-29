package DAL;

import BE.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO {
    private DatabaseConnector databaseConnector;

    public TeamDAO() {
        databaseConnector = new DatabaseConnector();
    }
    public List<Team> getAllTeams() {
        List<Team> allTeams = new ArrayList<>();
        String sql = "SELECT * FROM dbo.Teams;";
        try(Connection conn = databaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
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

    public Team createTeam(Team team) {
        String sql = "INSERT INTO dbo.Teams (name, country, type) VALUES (?,?,?);";
        try(Connection conn = databaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, team.getName());
            stmt.setString(2, team.getCountry());
            stmt.setString(3, team.getType().toString());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if(rs.next()) {
                id = rs.getInt(1);
            }

            Team createdTeam = new Team(id, team.getName(), team.getCountry(),team.getType());
            return createdTeam;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTeam(Team team) {
        String sql = "DELETE FROM dbo.Teams WHERE id = ?;";
        try(Connection conn = databaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, team.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Team updateTeam(Team team) {
        String sql = "UPDATE dbo.Teams SET name = ?, country = ?, type =? WHERE id = ?;";
        try(Connection conn = databaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, team.getName());
            stmt.setString(2, team.getCountry());
            stmt.setString(3, team.getType().toString());

            stmt.setInt(4, team.getId());

            stmt.executeUpdate();

            Team updatedTeam = new Team(team.getId(), team.getName(), team.getCountry(),team.getType());
            return updatedTeam;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
