package com.example.telas;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String PREF_NAME = "app_prefs";
    private static final String AUTH_TOKEN = "auth_token";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesManager(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveAuthToken(String token){
        editor.putString(AUTH_TOKEN, token);
        editor.apply();
    }

    public String getAuthToken(){
        return sharedPreferences.getString(AUTH_TOKEN, null);
    }

    public void clearAuthToken(){
        editor.remove(AUTH_TOKEN);
        editor.apply();
    }
}