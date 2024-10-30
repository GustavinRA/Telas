package com.example.telas.api;

import com.example.telas.model.ProfileRequest;
import com.example.telas.model.ProfileResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ProfileService {

    @POST("api/profiles")
    Call<ProfileResponse> createProfile(
            @Header("Authorization") String authHeader,
            @Body ProfileRequest profileRequest
    );

    @GET("api/profiles")
    Call<List<ProfileResponse>> getProfiles(
            @Header("Authorization") String authHeader
    );
}