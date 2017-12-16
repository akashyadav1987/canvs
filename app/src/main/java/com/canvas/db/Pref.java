package com.canvas.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by AkashYad on 8/15/2015.
 */
public class Pref {
    private Context context;
    public SharedPreferences preferences;
    private final String IS_HUNT_MODE_ON = "is_hunt_mode_on";
    private final String book_mark = "bookmarked";
    private final String seen = "seen";
    private final String fav = "fav";
    private final String fresh = "fresh";
    private final String nearby = "nearby";

    public Pref(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setHuntMode(boolean is_hunt) {
        if (preferences != null)
            preferences.edit().putBoolean(IS_HUNT_MODE_ON, is_hunt).apply();
    }

    public boolean getHuntMode() {
        if (preferences != null)
            return preferences.getBoolean(IS_HUNT_MODE_ON, false);
        else return false;
    }

    public void setBookmarkedFilter(boolean bookmarkedFilter) {
        if (preferences != null)
            preferences.edit().putBoolean(book_mark, bookmarkedFilter).apply();
    }

    public boolean getBookmarkedFilter() {
        if (preferences != null)
            return preferences.getBoolean(book_mark, false);
        else return false;
    }


    public void setSeenFilter(boolean seenFilter) {
        if (preferences != null)
            preferences.edit().putBoolean(seen, seenFilter).apply();
    }

    public boolean getSeenFilter() {
        if (preferences != null)
            return preferences.getBoolean(seen, false);
        else return false;
    }

    public void setFavFilter(boolean favFilter) {
        if (preferences != null)
            preferences.edit().putBoolean(fav, favFilter).apply();
    }

    public boolean getFavFilter() {
        if (preferences != null)
            return preferences.getBoolean(fav, false);
        else return false;
    }


    public void setFreshFilter(boolean freshFilter) {
        if (preferences != null)
            preferences.edit().putBoolean(fresh, freshFilter).apply();
    }

    public boolean getFreshFilter() {
        if (preferences != null)
            return preferences.getBoolean(fresh, false);
        else return false;
    }

    public void setNearbyFilter(boolean nearbyFilter) {
        if (preferences != null)
            preferences.edit().putBoolean(nearby, nearbyFilter).apply();
    }

    public boolean getNearbyFilter() {
        if (preferences != null)
            return preferences.getBoolean(nearby, false);
        else return false;
    }
    public void clearPref(){
       preferences.edit().clear().apply();

    }
}
