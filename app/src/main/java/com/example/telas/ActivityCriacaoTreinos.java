package com.example.telas;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas.api.ApiClient;
import com.example.telas.api.ExerciseService;
import com.example.telas.api.WorkoutService;
import com.example.telas.model.Exercise;
import com.example.telas.model.Workout;
import com.example.telas.model.WorkoutExercise;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity para criação de treinos.
 */
public class ActivityCriacaoTreinos extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CriacaoTreinoAdapter adapter;
    private EditText editTextSearch;
    private EditText editTextWorkoutName;
    private List<CriacaoTreinoExercicios> exercises = new ArrayList<>();
    private SharedPreferencesManager sharedPreferencesManager;
    private Button btnCreateWorkout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criacao_treinos);

        sharedPreferencesManager = new SharedPreferencesManager(this);

        recyclerView = findViewById(R.id.recyclerView);
        editTextSearch = findViewById(R.id.editTextText5);
        editTextWorkoutName = findViewById(R.id.editTextText4);
        btnCreateWorkout = findViewById(R.id.btnCreateWorkout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadExercises();

        editTextSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null) {
                    adapter.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
            }
        });

        btnCreateWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWorkout();
            }
        });
    }

    /**
     * Carrega os exercícios do back-end.
     */
    private void loadExercises() {
        ExerciseService exerciseService = ApiClient.getClient().create(ExerciseService.class);
        String token = sharedPreferencesManager.getAuthToken();

        Call<List<Exercise>> call = exerciseService.getExercises("Bearer " + token);
        call.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(@NonNull Call<List<Exercise>> call, @NonNull Response<List<Exercise>> response) {
                if (response.isSuccessful()) {
                    List<Exercise> exerciseResponse = response.body();
                    if (exerciseResponse != null) {
                        for (Exercise ex : exerciseResponse) {
                            exercises.add(new CriacaoTreinoExercicios(
                                    ex.getExerciseId(),
                                    ex.getNome(),
                                    String.join(", ", ex.getMusculosAfetados()),
                                    false
                            ));
                        }
                        adapter = new CriacaoTreinoAdapter(exercises, ActivityCriacaoTreinos.this);
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(ActivityCriacaoTreinos.this, "Erro ao carregar exercícios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Exercise>> call, @NonNull Throwable t) {
                Toast.makeText(ActivityCriacaoTreinos.this, "Erro de rede", Toast.LENGTH_SHORT).show();
                Log.e("CriacaoTreinos", t.getMessage());
            }
        });
    }

    /**
     * Salva o treino criado no back-end.
     */
    private void saveWorkout() {
        WorkoutService workoutService = ApiClient.getClient().create(WorkoutService.class);
        String token = sharedPreferencesManager.getAuthToken();

        Workout workout = new Workout();
        String workoutName = editTextWorkoutName.getText().toString().trim();
        if (workoutName.isEmpty()) {
            Toast.makeText(this, "Por favor, insira um nome para o treino.", Toast.LENGTH_SHORT).show();
            return;
        }
        workout.setNome(workoutName);

        List<WorkoutExercise> workoutExercises = new ArrayList<>();

        for (CriacaoTreinoExercicios ex : exercises) {
            if (ex.isAdded()) {
                if (ex.getSeries().isEmpty() || ex.getRepetitions().isEmpty() || ex.getRest().isEmpty()) {
                    Toast.makeText(this, "Por favor, preencha todos os campos para o exercício: " + ex.getName(), Toast.LENGTH_SHORT).show();
                    return;
                }

                WorkoutExercise we = new WorkoutExercise();
                Exercise exercise = new Exercise();
                exercise.setExerciseId(ex.getExerciseId());
                we.setExercise(exercise);
                we.setSeries(Integer.parseInt(ex.getSeries()));
                we.setRepetitions(Integer.parseInt(ex.getRepetitions()));
                we.setRest(Integer.parseInt(ex.getRest()));
                workoutExercises.add(we);
            }
        }

        if (workoutExercises.isEmpty()) {
            Toast.makeText(this, "Por favor, adicione pelo menos um exercício ao treino.", Toast.LENGTH_SHORT).show();
            return;
        }

        workout.setExercises(workoutExercises);

        // Salvar treino no SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("TREINOS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String workoutJson = gson.toJson(workout);
        editor.putString(workoutName, workoutJson);
        editor.apply();

        // Chamada da API
        Call<Workout> call = workoutService.createWorkout("Bearer " + token, workout);
        call.enqueue(new Callback<Workout>() {
            @Override
            public void onResponse(@NonNull Call<Workout> call, @NonNull Response<Workout> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ActivityCriacaoTreinos.this, "Treino salvo com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ActivityCriacaoTreinos.this, "Erro ao salvar treino", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Workout> call, @NonNull Throwable t) {
                Toast.makeText(ActivityCriacaoTreinos.this, "Erro de rede", Toast.LENGTH_SHORT).show();
                Log.e("SalvarTreino", t.getMessage());
            }
        });
    }
}