package com.example.telas.model;

import com.google.gson.annotations.SerializedName;

public class JwtResponse {
    @SerializedName("token")
    private String token;

    @SerializedName("username")
    private String username; // Adicionado

    public String getToken() {
        return token;
    }

    public String getUsername() { // Adicionado
        return username;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUsername(String username) { // Adicionado
        this.username = username;
    }
}