package BLL;

import BE.Profile;
import DAL.ProfileTeamDAO;
import util.Exception;

import java.util.List;

public class ProfileTeamManager {
    private final ProfileTeamDAO profileTeamDAO;

    public ProfileTeamManager() {
        profileTeamDAO = new ProfileTeamDAO();
    }

    public void addProfileToTeam(int profileId, int teamId) throws Exception {
        try {
            profileTeamDAO.addProfileToTeam(profileId, teamId);
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error adding profile to team", ex);
        }
    }

    public void removeProfileFromTeam(int profileId, int teamId) throws Exception {
        try {
            profileTeamDAO.removeProfileFromTeam(profileId, teamId);
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error removing profile from team", ex);
        }
    }

    public List<Profile> getProfilesForTeam(int teamId) throws Exception {
        try {
            return profileTeamDAO.getProfilesForTeam(teamId);
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error getting profiles for team", ex);
        }
    }
}
