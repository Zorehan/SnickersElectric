package util;

import BE.Profile;

public class Calculator {
    public double calcDayRate(Profile profile) {

        double dayRate = 0;
        return dayRate;
    }

    public double calcHourlyRate(Profile profile) {
        double effectiveHours = profile.getWorkHours() * (profile.getUtilizationPercent() / 100);
        double annualOverhead = profile.getAnnualSalary() * (profile.getOverheadPercent() / 100);
        double totalAnnualCost = profile.getAnnualSalary() + annualOverhead + profile.getAnnualAmount();

        double hourlyRate = totalAnnualCost / effectiveHours;
        return hourlyRate;
    }
}
