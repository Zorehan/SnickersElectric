package BE;

public class TeamProfiles {
    private int teamId;
    private int profileId;

    public TeamProfiles(int teamId, int profileId) {
        this.teamId = teamId;
        this.profileId = profileId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }
}
