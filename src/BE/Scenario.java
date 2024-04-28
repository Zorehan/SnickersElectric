package BE;

import java.util.List;

public class Scenario {
    private String name;
    private List<Profile> profilesList;
    private double hourlyRate;
    private double dayRate;

    public Scenario() {

        setHourlyRate();
        setDayRate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Profile> getProfilesList() {
        return profilesList;
    }

    public void setProfilesList(List<Profile> profilesList) {
        this.profilesList = profilesList;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate() {
        double hourlyRate = 0;
        for(Profile profile : profilesList) {
            hourlyRate += profile.getHourlyRate();
        }
        this.hourlyRate = hourlyRate;
    }

    public double getDayRate() {
        return dayRate;
    }

    public void setDayRate() {
        double dayRate = 0;
        for(Profile profile : profilesList) {
            dayRate += profile.getDailyRate();
        }
        this.dayRate = dayRate;
    }
}
