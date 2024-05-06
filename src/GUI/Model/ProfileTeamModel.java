package GUI.Model;

import BLL.ProfileTeamManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProfileTeamModel {
    private static ProfileTeamModel instance;
    private final ProfileTeamManager profileTeamManager;
    private final ObservableList<Integer> teamsForProfile;
    private final ObservableList<Integer> profilesForTeam;

    private int chosenProfileId;
    private int chosenTeamId;

    private ProfileTeamModel() {
        profileTeamManager = new ProfileTeamManager();
        teamsForProfile = FXCollections.observableArrayList();
        profilesForTeam = FXCollections.observableArrayList();
    }

    public static ProfileTeamModel getInstance() {
        if (instance == null) {
            instance = new ProfileTeamModel();
        }
        return instance;
    }

    public ObservableList<Integer> getTeamsForProfile(int profileId) {
        teamsForProfile.setAll(profileTeamManager.getTeamsForProfile(profileId));
        return teamsForProfile;
    }

    public ObservableList<Integer> getProfilesForTeam(int teamId) {
        profilesForTeam.setAll(profileTeamManager.getProfilesForTeam(teamId));
        return profilesForTeam;
    }

    public void addProfileToTeam(int profileId, int teamId) {
        profileTeamManager.addProfileToTeam(profileId, teamId);
    }

    public void removeProfileFromTeam(int profileId, int teamId) {
        profileTeamManager.removeProfileFromTeam(profileId, teamId);
    }
}
