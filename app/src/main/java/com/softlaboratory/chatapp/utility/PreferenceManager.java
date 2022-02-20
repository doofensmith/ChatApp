package com.softlaboratory.chatapp.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    //SHARED PREFERENCES
    private final SharedPreferences sharedPreferences;

    //CONSTRUCTOR
    public PreferenceManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(Constant.KEY_PREFERENCE,Context.MODE_PRIVATE);
    }

    //METHODS
    public void putBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public Boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
