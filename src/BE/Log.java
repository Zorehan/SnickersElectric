package BE;

public class Log {

    private String logText, profileName;
    private int id;

    public Log(int id, String profileName, String logText)
    {
        setId(id);
        setProfileName(profileName);
        setLogText(logText);
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getLogText() {
        return logText;
    }

    public void setLogText(String logText) {
        this.logText = logText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
