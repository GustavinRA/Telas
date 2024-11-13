package com.example.telas.api;

import com.example.telas.model.Workout;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface WorkoutService {

    @GET("api/workouts")
    Call<List<Workout>> getWorkouts(@Header("Authorization") String authHeader);

    @POST("api/workouts")
    Call<Workout> createWorkout(@Header("Authorization") String authHeader, @Body Workout workout);

    @PUT("api/workouts/{id}")
    Call<Workout> updateWorkout(@Header("Authorization") String authHeader, @Path("id") Long id, @Body Workout workout);

    @DELETE("api/workouts/{id}")
    Call<Void> deleteWorkout(@Header("Authorization") String authHeader, @Path("id") Long id);
}