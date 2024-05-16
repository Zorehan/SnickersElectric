package BE;

import java.util.List;

public class Scenario {
    private int id;
    private String name;
    private double hourlyRate;
    private double dayRate;
    private double grossMargin;
    private double markup;
    private double workHours;

    public Scenario(int id, String name, double hourlyRate, double dayRate, double grossMargin, double markup, double workHours) {
        this.id = id;
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.dayRate = dayRate;
        this.grossMargin = grossMargin;
        this.markup = markup;
        this.workHours = workHours;
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

    public double getDayRate() {
        return dayRate;
    }

    public double getGrossMargin() {
        return grossMargin;
    }

    public double getMarkup() {
        return markup;
    }
    public double getWorkHours() {
        return workHours;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void setDayRate(double dayRate) {
        this.dayRate = dayRate;
    }

    public void setGrossMargin(double grossMargin) {
        this.grossMargin = grossMargin;
    }

    public void setMarkup(double markup) {
        this.markup = markup;
    }

    public void setWorkHours(double workHours) {
        this.workHours = workHours;
    }
}
