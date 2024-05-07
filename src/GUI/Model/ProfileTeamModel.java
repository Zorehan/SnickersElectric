package GUI.Model;

import BE.Profile;
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

    public ObservableList<Profile> getProfilesForTeam(int id) {
        ObservableList<Profile> profiles = FXCollections.observableArrayList();
        profiles.setAll(profileTeamManager.getProfilesForTeam(id));
        return profiles;
    }


    public void addProfileToTeam(int profileId, int teamId) {
        profileTeamManager.addProfileToTeam(profileId, teamId);
    }

    public void removeProfileFromTeam(int profileId, int teamId) {
        profileTeamManager.removeProfileFromTeam(profileId, teamId);
    }
}
