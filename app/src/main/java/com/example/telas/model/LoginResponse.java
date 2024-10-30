package com.example.telas.model;

public class LoginResponse {
    private String token;
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