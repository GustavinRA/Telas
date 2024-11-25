package com.example.telas.bibliotecaTreinos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas.ActivityCriacaoTreinos;
import com.example.telas.R;
import com.example.telas.model.Workout;
import com.example.telas.model.WorkoutExercise;
import com.example.telas.model.Exercise;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivityBibliotecaTreino extends AppCompatActivity {
    private ImageButton btnAddExercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca_treino);

        btnAddExercicio = findViewById(R.id.btn_addTreino);
        btnAddExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityBibliotecaTreino.this, ActivityCriacaoTreinos.class);
                startActivity(intent);
            }
        });

        RecyclerView rvTreinos = findViewById(R.id.rvTreinos);
        rvTreinos.setLayoutManager(new LinearLayoutManager(this));

        List<Workout> workouts = loadWorkouts();  // Carregar os treinos salvos

        AdapterTraining adapter = new AdapterTraining(workouts);
        rvTreinos.setAdapter(adapter);
    }

    /**
     * Carrega os treinos salvos em SharedPreferences.
     */
    private List<Workout> loadWorkouts() {
        List<Workout> workouts = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("TREINOS", MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();

        Gson gson = new Gson();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String workoutJson = (String) entry.getValue();
            Workout workout = gson.fromJson(workoutJson, Workout.class);

            workouts.add(workout);
        }

        return workouts;
    }
}
