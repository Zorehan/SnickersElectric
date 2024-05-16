package BE;

import util.Calculator;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Profile {
    private final PropertyChangeSupport propertyChangeSupport;
    private String name, country;
    private ProfileType type;
    private double annualSalary, overheadPercent
            ,utilizationPercent, annualAmount
            ,workHours, hourlyRate, dailyRate;
    private int id;

    // Denne kunne godt være sin egen Class, men det kan vi ret nemt refactor hvis vi føler det er nødvændigt
    // Det finder vi bare ud af engang.
    public enum ProfileType {
        OVERHEAD_COST("Overhead Cost"),
        PRODUCTION_RESOURCE("Production Resource");

        private final String displayName;

        ProfileType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }


    public Profile(int id, String name, double annualSalary, double workHours, double annualAmount, double overheadPercent, double utilizationPercent, String country, ProfileType type) {
        setId(id);
        setName(name);
        setAnnualSalary(annualSalary);
        setWorkHours(workHours);
        setAnnualAmount(annualAmount);
        setOverheadPercent(overheadPercent);
        setUtilizationPercent(utilizationPercent);
        setCountry(country);
        setType(type);
        setHourlyRate();
        propertyChangeSupport = new PropertyChangeSupport(this);
        setDailyRate(10.0);

    }


    /*
     *  ----------------- GETTERS OG SETTERS -----------------
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ProfileType getType() {
        return type;
    }

    public void setType(ProfileType type) {
        this.type = type;
    }

    public double getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(double annualSalary) {
        this.annualSalary = annualSalary;
    }

    public double getOverheadPercent() {
        return overheadPercent;
    }

    public void setOverheadPercent(double overheadPercent) {
        this.overheadPercent = overheadPercent;
    }

    public double getUtilizationPercent() {
        return utilizationPercent;
    }

    public void setUtilizationPercent(double utilizationPercent) {
        this.utilizationPercent = utilizationPercent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getWorkHours() {
        return workHours;
    }

    public void setWorkHours(double workHours) {
        this.workHours = workHours;
    }

    public double getAnnualAmount() {
        return annualAmount;
    }

    public void setAnnualAmount(double annualAmount) {
        this.annualAmount = annualAmount;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate() {
        double rate = Calculator.calcHourlyRate(this);
        DecimalFormat df = new DecimalFormat("#.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        this.hourlyRate = Double.parseDouble(df.format(rate));
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(Double hours) {
        double oldRate = this.dailyRate;
        double newRate = Calculator.calcDayRate(this, hours);
        DecimalFormat df = new DecimalFormat("#.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        this.dailyRate = Double.parseDouble(df.format(newRate));
        propertyChangeSupport.firePropertyChange("dailyRate", oldRate, this.dailyRate);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return id == profile.id;
    }
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
