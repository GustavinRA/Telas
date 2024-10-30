package com.example.telas.model;

import com.google.gson.annotations.SerializedName;

public class JwtResponse {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @SerializedName("token")
    private String token;
}
