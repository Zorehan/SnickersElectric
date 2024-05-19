package BE;

public class Log {

    private String logText;
    private int id, referenceProfileId;

    public Log(int id, int referenceProfileId, String logText)
    {
        setId(id);
        setReferenceProfileId(referenceProfileId);
        setLogText(logText);
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

    public int getReferenceProfileId() {
        return referenceProfileId;
    }

    public void setReferenceProfileId(int referenceProfileId) {
        this.referenceProfileId = referenceProfileId;
    }
}
