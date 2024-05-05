package BE;

import java.util.List;

public class Scenario {
    private int id;
    private String name;
    private double hourlyRate;
    private double dayRate;

    public Scenario(int id, String name, double hourlyRate, double dayRate) {
        this.id = id;
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.dayRate = dayRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getHourlyRate() {
        return hourlyRate;
    }


    public double getDailyRate() {
        return dayRate;
    }

}
