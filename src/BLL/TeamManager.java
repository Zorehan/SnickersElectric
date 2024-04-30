package BLL;

import BE.Team;
import DAL.TeamDAO;

import java.util.List;

public class TeamManager {
    private TeamDAO teamDAO;
    public TeamManager() {
        teamDAO = new TeamDAO();
    }

    public List<Team> getAllTeams() {
        return teamDAO.getAll();
    }

    public Team createTeam(Team newTeam) {
        return teamDAO.create(newTeam);
    }

    public void deleteTeam(Team team) {
        teamDAO.delete(team);
    }

    public Team updateTeam(Team team) {
        return teamDAO.update(team);
    }
}
