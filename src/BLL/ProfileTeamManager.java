package BLL;

import DAL.ProfileTeamDAO;

import java.util.List;

public class ProfileTeamManager {
    private final ProfileTeamDAO profileTeamDAO;

    public ProfileTeamManager() {
        profileTeamDAO = new ProfileTeamDAO();
    }

    public void addProfileToTeam(int profileId, int teamId) {
        profileTeamDAO.addProfileToTeam(profileId, teamId);
    }

    public void removeProfileFromTeam(int profileId, int teamId) {
        profileTeamDAO.removeProfileFromTeam(profileId, teamId);
    }

    public List<Integer> getTeamsForProfile(int profileId) {
        return profileTeamDAO.getTeamsForProfile(profileId);
    }

    public List<Integer> getProfilesForTeam(int teamId) {
        return profileTeamDAO.getProfilesForTeam(teamId);
    }
}
