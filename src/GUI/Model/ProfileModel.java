package GUI.Model;

import BE.Profile;
import BLL.ProfileManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProfileModel {
    private static ProfileModel instance;
    private ProfileManager profileManager;
    private Profile profile;
    private ObservableList<Profile> allProfiles;
    private ObservableList<Profile> historicProfile;

    private Profile chosenProfile;

    private ProfileModel() {
        profileManager = new ProfileManager();

        allProfiles = FXCollections.observableArrayList();
        allProfiles.addAll(profileManager.getAllProfiles());
    }

    public static ProfileModel getInstance() {
        if(instance == null) {
            instance = new ProfileModel();
        }
        return instance;
    }

    public ObservableList<Profile> getObservableProfiles() {
        return allProfiles;
    }

    public Profile createProfile(Profile newProfile) {
        Profile profile = profileManager.createProfile(newProfile);
        allProfiles.add(profile);
        return profile;
    }

    public void deleteProfile(Profile profile) {
        profileManager.deleteProfile(profile);
        allProfiles.remove(profile);
    }

    public void setChosenProfile(Profile profile)
    {
        chosenProfile = profile;
    }

    public Profile getChosenProfile()
    {
        return chosenProfile;
    }

    public void updateProfile(Profile profile) {
        Profile newProfile = profileManager.updateProfile(profile);
        allProfiles.remove(profile);
        allProfiles.add(newProfile);
    }

    public ObservableList<Profile> getHistoricProfile(Profile profile) {
        historicProfile = FXCollections.observableArrayList();
        historicProfile.addAll(profileManager.getHistoricProfile(profile));
        return historicProfile;
    }

    public Profile getProfileById(int id)
    {
        for (Profile profile : allProfiles)
        {
            if(profile.getId() == id)
            {
                return profile;
            }
        }
        return null;
    }

}
