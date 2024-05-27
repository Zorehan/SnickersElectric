package UnitTest;

import BE.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Calculator;

import static org.junit.jupiter.api.Assertions.*;

public class Calculatortest {

    private Profile profile;

    @BeforeEach
    public void setUp() {
        profile = new Profile(1, "John Doe", 60000.0, 2000.0, 10000.0, 20.0, 80.0, "Denmark", Profile.ProfileType.PRODUCTION_RESOURCE);
    }

    @Test
    public void testCalcDayRate() {
        double hours = 8;
        double actualDayRate = Calculator.calcDayRate(profile, hours);
        System.out.println("Actual Day Rate: " + actualDayRate);
        double expectedDayRate = profile.getHourlyRate() * hours;
        assertEquals(expectedDayRate, actualDayRate, 0.001);
    }

    @Test
    public void testCalcHourlyRate() {
        double effectiveHours = 2000.0 * (80.0 / 100);
        double annualOverhead = 60000.0 * (20.0 / 100);
        double totalAnnualCost = 60000.0 + 12000.0 + 10000.0;
        double expectedHourlyRate = 82000.0 / 1600.0;
        double actualHourlyRate = Calculator.calcHourlyRate(profile);
        assertEquals(expectedHourlyRate, actualHourlyRate, 0.001);
    }

    @Test
    public void testCalcGrossMargin() {
        double value = 100.0;
        double margin = 20.0;
        double expectedGrossMargin = 125.0; // 100.0 / (1 - 0.2) = 100.0 / 0.8 = 125.0
        double actualGrossMargin = Calculator.calcGrossMargin(value, margin);
        assertEquals(expectedGrossMargin, actualGrossMargin, 0.001);
    }

    @Test
    public void testCalcMarkup() {
        double value = 100.0;
        double markup = 20.0;
        double expectedMarkup = 120.0; // 100.0 * (1 + 0.2) = 100.0 * 1.2 = 120.0
        double actualMarkup = Calculator.calcMarkup(value, markup);
        assertEquals(expectedMarkup, actualMarkup, 0.001);
    }
}