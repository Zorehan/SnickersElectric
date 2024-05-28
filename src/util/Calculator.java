package util;

import BE.Profile;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Calculator {
    static DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.forLanguageTag("da_DK"));
    private static final DecimalFormat df = new DecimalFormat("#.00", symbols);

    public static double calcDayRate(Profile profile, double hours) {
        double dayRate = profile.getHourlyRate() * (hours / 365);
        return dayRate;
    }

    public static double calcHourlyRate(Profile profile) {
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

    /**
     *
     * @param value The initial value of something that needs to be multiplied with a margin.
     * @param margin The margin multiplier
     * @return a calculated gross margin
     */
    public static double calcGrossMargin(double value, double margin) {
        margin = 1 - (margin / 100);
        double calculated =  value / margin;
        return Double.parseDouble(df.format(calculated));
    }

    /**
     *
     * @param value The initial value of something that needs to be multiplied with a markup.
     * @param markup The markup multiplier
     * @return a calculated markup
     */
    public static double calcMarkup(double value, double markup) {
        markup = 1 + (markup / 100);
        double calculated = value * markup;
        return Double.parseDouble(df.format(calculated));
    }
}