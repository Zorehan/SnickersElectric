package BE;

import java.time.LocalDate;

public class HistoricProfile extends Profile{
    private LocalDate date;
    public HistoricProfile(int id, String name, double annualSalary, double workHours, double annualAmount, double overheadPercent, double utilizationPercent, String country, ProfileType type, LocalDate date) {
        super(id, name, annualSalary, workHours, annualAmount, overheadPercent, utilizationPercent, country, type);
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
