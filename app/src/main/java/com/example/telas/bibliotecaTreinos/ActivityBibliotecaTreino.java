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
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class ActivityBibliotecaTreino extends AppCompatActivity {
    private ImageButton btnAddExercicio;
    private RecyclerView rvTreinos;

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

        rvTreinos = findViewById(R.id.rvTreinos);
        rvTreinos.setLayoutManager(new LinearLayoutManager(this));

        List<Workout> workouts = loadWorkouts();  // Carregar os treinos salvos

        AdapterTraining adapter = new AdapterTraining(workouts);
        rvTreinos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Workout> workouts = loadWorkouts();  // Recarrega os treinos salvos
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
        Type workoutType = new TypeToken<Workout>(){}.getType();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String workoutJson = (String) entry.getValue();
            Workout workout = gson.fromJson(workoutJson, workoutType);

            // Log do JSON do treino
            Log.d("LoadWorkouts", "Workout JSON: " + workoutJson);

            // Verificar cada exercício
            for (WorkoutExercise we : workout.getExercises()) {
                Exercise ex = we.getExercise();
                if (ex != null) {
                    Log.d("LoadWorkouts", "Exercise ID: " + ex.getExerciseId() + ", Nome: " + ex.getNome() + ", Músculos: " + ex.getMusculosAfetados());
                } else {
                    Log.e("LoadWorkouts", "Exercise is null in WorkoutExercise");
                }
            }

            workouts.add(workout);
        }

        return workouts;
    }

}
