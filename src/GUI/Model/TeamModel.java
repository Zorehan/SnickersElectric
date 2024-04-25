package GUI.Model;

import BE.Profile;
import BE.Team;
import BLL.ProfileManager;
import BLL.TeamManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TeamModel {
    private static TeamModel instance;
    private TeamManager teamManager;
    private Team team;
    private ObservableList<Team> allTeams;

    public TeamModel() {
            teamManager = new TeamManager();

            allTeams = FXCollections.observableArrayList();
            allTeams.addAll(teamManager.getAllTeams());
        }

    public static TeamModel getInstance() {
        if(instance == null) {
            instance = new TeamModel();
        }
        return instance;
    }
    public ObservableList<Team> getObservableTeams() {
        return allTeams;
    }
    public Team createTeam(Team newTeam) {
        Team team = teamManager.createTeam(newTeam);
        allTeams.add(team);
        return team;
    }
    public void deleteTeam(Team team) {
        teamManager.deleteTeam(team);
        allTeams.remove(team);
    }
    public void updateTeam(Team team) {
        teamManager.updateTeam(team);
    }
}
