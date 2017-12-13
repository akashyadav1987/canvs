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





    public void clearPref(){
       preferences.edit().clear().apply();

    }
}
