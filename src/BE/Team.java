package BE;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private int id;
    private String name, country;
    private List<Profile> profiles;
    private TeamType type;

    // Denne kunne godt være sin egen Class, men det kan vi ret nemt refactor hvis vi føler det er nødvændigt
    // Det finder vi bare ud af engang.
    public enum TeamType {
        OVERHEAD_COST("Overhead Cost"),
        PRODUCTION_RESOURCE("Production Resource");

        private final String displayName;

        TeamType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }


    /**
     * Kontruktøren til at lave et Team UDEN profiler tilknyttet!
     */
    public Team (int id, String name, String country, TeamType type){
        this.profiles = new ArrayList<>();
        setId(id);
        setCountry(country);
        setName(name);
        setType(type);
    }

    /**
     * Konstruktøren til at lave et Team MED profiler tilknyttet.
     * @param profiles en liste af alle forbundene profiles
     */
    public Team (int id, String name, String country, List<Profile> profiles, TeamType type){
        this.profiles = new ArrayList<>();
        setId(id);
        setName(name);
        setCountry(country);
        setProfiles(profiles);
        setType(type);
    }

    public void addProfile(Profile profile) {
        profiles.add(profile);
    }

    public void removeProfile(Profile profile) {
        profiles.remove(profile);
    }

    /*
     *  ----------------- GETTERS OG SETTERS -----------------
     */
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public TeamType getType() {
        return type;
    }

    public void setType(TeamType type) {
        this.type = type;
    }
}
