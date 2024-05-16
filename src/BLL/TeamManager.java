package BLL;

import BE.Team;
import DAL.TeamDAO;
import util.Exception; // Importing the Exception class from the util package

import java.util.List;

public class TeamManager {
    private final TeamDAO teamDAO;

    public TeamManager() {
        teamDAO = new TeamDAO();
    }

    public List<Team> getAllTeams() throws Exception {
        try {
            return teamDAO.getAll();
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error getting all teams", ex);
        }
    }

    public Team createTeam(Team newTeam) throws Exception {
        try {
            return teamDAO.create(newTeam);
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error creating team", ex);
        }
    }

    public void deleteTeam(Team team) throws Exception {
        try {
            teamDAO.delete(team);
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error deleting team", ex);
        }
    }

    public Team updateTeam(Team team) throws Exception {
        try {
            return teamDAO.update(team);
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error updating team", ex);
        }
    }
}
