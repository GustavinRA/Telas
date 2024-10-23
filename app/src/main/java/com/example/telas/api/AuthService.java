package com.example.telas.api;

import com.example.telas.model.LoginRequest;
import com.example.telas.model.LoginResponse;
import com.example.telas.model.RegisterRequest;
import com.example.telas.model.MessageResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("api/auth/register")
    Call<MessageResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST("api/auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}