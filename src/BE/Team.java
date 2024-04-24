package BE;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private int id;
    private String name, country;
    private TeamType type;
    private List<Profile> profiles;

    public enum TeamType {
        ADMINISTRATION
    }

    /**
     * Kontruktøren til at lave et Team UDEN profiler tilknyttet!
     */
    public Team (int id, String name, String country, TeamType type){
        this.profiles = new ArrayList<>();
        setId(id);
        setName(name);
        setCountry(country);
        setType(type);
    }

    /**
     * Konstruktøren til at lave et Team MED profiler tilknyttet.
     * @param profiles en liste af alle forbundene profiles
     */
    public Team (int id, String name, String country, TeamType type, List<Profile> profiles){
        this.profiles = new ArrayList<>();
        setId(id);
        setName(name);
        setCountry(country);
        setType(type);
        setProfiles(profiles);
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

    public TeamType getType() {
        return type;
    }

    public void setType(TeamType type) {
        this.type = type;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }
}
