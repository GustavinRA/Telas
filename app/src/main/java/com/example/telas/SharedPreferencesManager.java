package com.example.telas;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SharedPreferencesManager {
    private static final String SHARED_PREF_NAME = "my_app_prefs";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EXERCICIOS = "exercicios_key";  // Nova chave para armazenar os exercícios
    private SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    // Método para salvar o token de autenticação
    public void saveAuthToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_AUTH_TOKEN, token);
        editor.apply();
    }

    // Método para obter o token de autenticação
    public String getAuthToken() {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null);
    }

    // Método para salvar o nome do usuário
    public void saveUsername(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    // Método para obter o nome do usuário
    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "Usuário");
    }

    // Método para salvar a lista de exercícios
    public void saveExercicios(List<Exercicio> exercicios) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(exercicios);
        editor.putString(KEY_EXERCICIOS, json);
        editor.apply();
    }

    // Método para obter a lista de exercícios
    public List<Exercicio> getExercicios() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_EXERCICIOS, null);
        Type type = new TypeToken<List<Exercicio>>() {}.getType();
        if (json != null) {
            return gson.fromJson(json, type);
        } else {
            return null;  // Retorna null caso não haja dados salvos
        }
    }

    // Método para limpar todas as preferências
    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
