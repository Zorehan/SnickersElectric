package util;

import BE.Profile;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class SearchEngine {

    private final FilteredList<Profile> filteredProfiles;

    public SearchEngine (ObservableList<Profile> data)
    {
        filteredProfiles = new FilteredList<>(data, p -> true);
    }

    public FilteredList<Profile> getFilteredProfiles()
    {
        return filteredProfiles;
    }

    public void filter(String query) {
        filteredProfiles.setPredicate(profile -> {

            if (query == null || query.isEmpty()) {
                return true;
            }

            String[] queryList = query.toLowerCase().split(",");


            String profileName = profile.getName().toLowerCase();
            String profileCountry = profile.getCountry().toLowerCase();
            String profileAnnualSalary = String.valueOf(profile.getAnnualSalary()).toLowerCase();
            String profileHourlyRate = String.valueOf(profile.getHourlyRate());
            String profileDailyRate = String.valueOf(profile.getDailyRate());


            for (String queries : queryList) {
                String splitQueries = queries.trim();
                if (profileName.contains(splitQueries) ||
                        profileCountry.contains(splitQueries) ||
                        profileAnnualSalary.contains(splitQueries) ||
                        profileHourlyRate.contains(splitQueries) ||
                        profileDailyRate.contains(splitQueries))
                {
                    return true;
                }
            }

            return false;
        });
    }
}
