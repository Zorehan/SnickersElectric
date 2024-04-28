package BLL;

import BE.Profile;
import DAL.ProfileDAO;

import java.util.List;

public class ProfileManager {
    private ProfileDAO profileDAO;
    public ProfileManager() {
        profileDAO = new ProfileDAO();
    }

    public List<Profile> getAllProfiles() {
        return profileDAO.getAllProfiles();
    }

    public Profile createProfile(Profile profile) {
        return profileDAO.createProfile(profile);
    }

    public void deleteProfile(Profile profile) {
        profileDAO.deleteProfile(profile);
    }

    public Profile updateProfile(Profile profile) {
        return profileDAO.updateProfile(profile);
    }
}
