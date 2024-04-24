package BE;

public class Template {
    private String name, country;
    private double annualSalary, overheadPercent, utilizationPercent;
    private int id, workHours, annualAmount;

    private Template(int id, String name, String country, double annualSalary, double overheadPercent, double utilizationPercent, int workHours, int annualAmount) {
        setId(id);
        setName(name);
        setAnnualSalary(annualSalary);
        setWorkHours(workHours);
        setAnnualAmount(annualAmount);
        setOverheadPercent(overheadPercent);
        setUtilizationPercent(utilizationPercent);
        setCountry(country);
    }

    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    private String getCountry() {
        return country;
    }

    private void setCountry(String country) {
        this.country = country;
    }

    private double getAnnualSalary() {
        return annualSalary;
    }

    private void setAnnualSalary(double annualSalary) {
        this.annualSalary = annualSalary;
    }

    private double getOverheadPercent() {
        return overheadPercent;
    }

    private void setOverheadPercent(double overheadPercent) {
        this.overheadPercent = overheadPercent;
    }

    private double getUtilizationPercent() {
        return utilizationPercent;
    }

    private void setUtilizationPercent(double utilizationPercent) {
        this.utilizationPercent = utilizationPercent;
    }

    private int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    private int getWorkHours() {
        return workHours;
    }

    private void setWorkHours(int workHours) {
        this.workHours = workHours;
    }

    private int getAnnualAmount() {
        return annualAmount;
    }

    private void setAnnualAmount(int annualAmount) {
        this.annualAmount = annualAmount;
    }
}
