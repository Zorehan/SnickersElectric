package BE;

public class Profile {
    private String name;
    private String country;
    private ProfileType type;
    private double annualSalary;
    private int workHours;
    private int annualAmount;
    private double overheadPercent;
    private double utilizationPercent;

    // Denne kunne godt være sin egen Class, men det kan vi ret nemt refactor hvis vi føler det er nødvændigt
    // Det finder vi bare ud af engang.
    public enum ProfileType {
        TEAM_LEAD, TEAM_MEMBER, CONTRACTOR
    }

    public Profile(String name, double annualSalary, int workHours, int annualAmount, double overheadPercent, double utilizationPercent, String country, ProfileType type) {
        setName(name);
        setAnnualSalary(annualSalary);
        setWorkHours(workHours);
        setAnnualAmount(annualAmount);
        setOverheadPercent(overheadPercent);
        setUtilizationPercent(utilizationPercent);
        setCountry(country);
        setType(type);
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setAnnualSalary(double annualSalary) {
        this.annualSalary = annualSalary;
    }

    private void setAnnualAmount(int annualAmount) {
        this.annualAmount = annualAmount;
    }

    private void setCountry(String country) {
        this.country = country;
    }

    private void setOverheadPercent(double overheadPercent) {
        this.overheadPercent = overheadPercent;
    }

    private void setType(ProfileType type) {
        this.type = type;
    }

    private void setUtilizationPercent(double utilizationPercent) {
        this.utilizationPercent = utilizationPercent;
    }

    private void setWorkHours(int workHours) {
        this.workHours = workHours;
    }

    private String getName() {
        return name;
    }

    private double getAnnualSalary() {
        return annualSalary;
    }

    private double getOverheadPercent() {
        return overheadPercent;
    }

    private double getUtilizationPercent() {
        return utilizationPercent;
    }

    private int getAnnualAmount() {
        return annualAmount;
    }

    private int getWorkHours() {
        return workHours;
    }

    private ProfileType getType() {
        return type;
    }

    private String getCountry() {
        return country;
    }
}
