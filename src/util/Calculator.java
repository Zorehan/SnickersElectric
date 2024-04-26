package util;

import BE.Profile;

public class Calculator {
    public double calcDayRate(Profile profile, int hours) {
        double dayRate = profile.getHourlyRate() * hours;
        return dayRate;
    }

    public double calcHourlyRate(Profile profile) {
        //Udregner effective work hours baseret på den givende util %
        double effectiveHours = profile.getWorkHours() * (profile.getUtilizationPercent() / 100);
        //Udregner overhead som et procentdel af årslønnen
        double annualOverhead = profile.getAnnualSalary() * (profile.getOverheadPercent() / 100);
        // Plusser årsløn sammen med overhead og den faste overhead (Anuual Amount)
        double totalAnnualCost = profile.getAnnualSalary() + annualOverhead + profile.getAnnualAmount();

        // Divderer det hele med mængden af effective hours på et år, for at få prisen på timebasis
        double hourlyRate = totalAnnualCost / effectiveHours;
        return hourlyRate;
    }
}