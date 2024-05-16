package BLL;

import BE.Profile;
import DAL.ProfileDAO;
import util.Exception;

import java.util.List;

public class ProfileManager {
    private final ProfileDAO profileDAO;

    public ProfileManager() {
        profileDAO = new ProfileDAO();
    }

    public List<Profile> getAllProfiles() throws Exception {
        try {
            return profileDAO.getAll();
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error getting all profiles", ex);
        }
    }

    public Profile createProfile(Profile profile) throws Exception {
        try {
            return profileDAO.create(profile);
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error creating profile", ex);
        }
    }

    public void deleteProfile(Profile profile) throws Exception {
        try {
            profileDAO.delete(profile);
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error deleting profile", ex);
        }
    }

    public Profile updateProfile(Profile profile) throws Exception {
        try {
            return profileDAO.update(profile);
        } catch (Exception ex) {
            // Handle the exception or rethrow it if needed
            throw new Exception("Error updating profile", ex);
        }
    }
}
