package com.example.telas;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

public class SharedPreferencesManager {
    private static final String PREF_NAME = "PeakPlannerPrefs";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context context){
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferences = EncryptedSharedPreferences.create(
                    PREF_NAME,
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            // Fallback para SharedPreferences não encriptado
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
    }

    public void saveAuthToken(String token){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_AUTH_TOKEN, token);
        editor.apply();
    }

    public String getAuthToken(){
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null);
    }

    public void clearAuthToken(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_AUTH_TOKEN);
        editor.apply();
    }
}