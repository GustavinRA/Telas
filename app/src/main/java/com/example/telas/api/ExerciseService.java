package com.example.telas.api;

import com.example.telas.model.Exercise;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ExerciseService {

    @GET("api/exercises")
    Call<List<Exercise>> getExercises(@Header("Authorization") String authHeader);

    @POST("api/exercises")
    Call<Exercise> createExercise(@Header("Authorization") String authHeader, @Body Exercise exercise);

    @PUT("api/exercises/{id}")
    Call<Exercise> updateExercise(@Header("Authorization") String authHeader, @Path("id") Long id, @Body Exercise exercise);

    @DELETE("api/exercises/{id}")
    Call<Void> deleteExercise(@Header("Authorization") String authHeader, @Path("id") Long id);
}