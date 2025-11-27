package com.example.assignment1;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesManager {
    private static final String PREF_NAME = "attraction_planner_pref";
    private static final String ATTRACTION_COUNT_KEY = "attraction_count";
    private static final String ATTRACTION_PREFIX = "attraction_";

    private SharedPreferences sharedPreferences;
    public SharedPreferencesManager(Context context){
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
    }


    public void addAttraction(Attraction attraction) {
        int count = sharedPreferences.getInt(ATTRACTION_COUNT_KEY, 0);
        String prefix = ATTRACTION_PREFIX + count + "_";

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(prefix + "id", attraction.getAttractionId());
        editor.putString(prefix + "name", attraction.getName());
        editor.putString(prefix + "location", attraction.getLocation());
        editor.putString(prefix + "type", attraction.getType());
        editor.putInt(prefix + "rating", attraction.getRating());
        editor.putLong(prefix + "fee", (long) attraction.getEntryFee());
        editor.putString(prefix + "date", attraction.getVisitDate());
        editor.putString(prefix + "time", attraction.getVisitTime());
        editor.putInt(prefix + "duration", attraction.getDuration());
        editor.putBoolean(prefix + "visited", attraction.isVisited());
        editor.putBoolean(prefix + "favorite", attraction.isFavorite());
        editor.putString(prefix + "notes", attraction.getNotes());
        editor.putInt(ATTRACTION_COUNT_KEY, count + 1);
        editor.apply();
    }


    public List<Attraction> getAllAttractions() {
        List<Attraction> attractions = new ArrayList<>();
        int count = sharedPreferences.getInt(ATTRACTION_COUNT_KEY, 0);

        for (int i = 0; i < count; i++) {
            Attraction attraction = getAttractionByIndex(i);
            if (attraction != null) {
                attractions.add(attraction);
            }
        }
        return attractions;
    }


    private Attraction getAttractionByIndex(int index) {
        String prefix = ATTRACTION_PREFIX + index + "_";
        String id = sharedPreferences.getString(prefix + "id", null);

        if (id == null) {
            return null;
        }

        Attraction attraction = new Attraction();
        attraction.setAttractionId(id);
        attraction.setName(sharedPreferences.getString(prefix + "name", ""));
        attraction.setLocation(sharedPreferences.getString(prefix + "location", ""));
        attraction.setType(sharedPreferences.getString(prefix + "type", ""));
        attraction.setRating(sharedPreferences.getInt(prefix + "rating", 1));
        attraction.setEntryFee(sharedPreferences.getLong(prefix + "fee", 0));
        attraction.setVisitDate(sharedPreferences.getString(prefix + "date", ""));
        attraction.setVisitTime(sharedPreferences.getString(prefix + "time", ""));
        attraction.setDuration(sharedPreferences.getInt(prefix + "duration", 0));
        attraction.setVisited(sharedPreferences.getBoolean(prefix + "visited", false));
        attraction.setFavorite(sharedPreferences.getBoolean(prefix + "favorite", false));
        attraction.setNotes(sharedPreferences.getString(prefix + "notes", ""));

        return attraction;
    }


    public Attraction getAttractionById(String attractionId) {
        List<Attraction> attractions = getAllAttractions();
        for (Attraction a : attractions) {
            if (a.getAttractionId().equals(attractionId)) {
                return a;
            }
        }
        return null;
    }



    public void updateAttraction(Attraction updatedAttraction) {
        List<Attraction> attractions = getAllAttractions();

        // Clear all data
        sharedPreferences.edit().clear().apply();

        // Re-save all attractions with updated one
        sharedPreferences.edit().putInt(ATTRACTION_COUNT_KEY, 0).apply();

        for (Attraction a : attractions) {
            if (a.getAttractionId().equals(updatedAttraction.getAttractionId())) {
                addAttraction(updatedAttraction);
            } else {
                addAttraction(a);
            }
        }
    }

    // Delete attraction
    public void deleteAttraction(String attractionId) {
        List<Attraction> attractions = getAllAttractions();

        // Clear all data
        sharedPreferences.edit().clear().apply();

        // Re-save all attractions except deleted one
        sharedPreferences.edit().putInt(ATTRACTION_COUNT_KEY, 0).apply();

        for (Attraction a : attractions) {
            if (!a.getAttractionId().equals(attractionId)) {
                addAttraction(a);
            }
        }
    }

    public List<Attraction> searchAttractions(String query) {
        List<Attraction> allAttractions = getAllAttractions();
        List<Attraction> results = new ArrayList<>();

        for (Attraction a : allAttractions) {
            if (a.getName().toLowerCase().contains(query.toLowerCase()) ||
                    a.getLocation().toLowerCase().contains(query.toLowerCase())) {
                results.add(a);
            }
        }
        return results;
    }
}
















