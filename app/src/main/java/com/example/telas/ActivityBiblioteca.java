package com.example.telas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas.api.ApiClient;
import com.example.telas.api.ExerciseService;
import com.example.telas.model.Exercise;
import com.example.telas.recyclerviewTreinos.ExercicioAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityBiblioteca extends AppCompatActivity {

    private RecyclerView recyclerViewBiblioteca;
    private ExercicioAdapter adapter;
    private List<Exercise> exerciseList;
    private SharedPreferencesManager sharedPreferencesManager;
    private View emptyStateLayout;
    private View contentLayout;
    private ImageButton btnAddExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca);

        sharedPreferencesManager = new SharedPreferencesManager(this);

        recyclerViewBiblioteca = findViewById(R.id.recyclerBiblioteca);
        recyclerViewBiblioteca.setLayoutManager(new LinearLayoutManager(this));

        emptyStateLayout = findViewById(R.id.empty_state_layout);
        contentLayout = findViewById(R.id.content_layout);
        btnAddExercise = findViewById(R.id.btn_addExercise);

        btnAddExercise.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityBiblioteca.this, ActivityCriacaoExercicios.class);
            startActivity(intent);
        });

        findViewById(R.id.buttonCreateExercise).setOnClickListener(v -> {
            Intent intent = new Intent(ActivityBiblioteca.this, ActivityCriacaoExercicios.class);
            startActivity(intent);
        });

        loadExercises();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadExercises();
    }

    private void loadExercises() {
        ExerciseService exerciseService = ApiClient.getClient().create(ExerciseService.class);
        String token = sharedPreferencesManager.getAuthToken();

        Call<List<Exercise>> call = exerciseService.getExercises("Bearer " + token);
        call.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                if (response.isSuccessful()) {
                    exerciseList = response.body();

                    if (exerciseList != null && !exerciseList.isEmpty()) {
                        recyclerViewBiblioteca.setVisibility(View.VISIBLE);
                        emptyStateLayout.setVisibility(View.GONE);

                        adapter = new ExercicioAdapter(ActivityBiblioteca.this, exerciseList);
                        recyclerViewBiblioteca.setAdapter(adapter);
                    } else {
                        recyclerViewBiblioteca.setVisibility(View.GONE);
                        emptyStateLayout.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(ActivityBiblioteca.this, "Erro ao carregar exercícios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {
                Toast.makeText(ActivityBiblioteca.this, "Erro de rede", Toast.LENGTH_SHORT).show();
                Log.e("Biblioteca", t.getMessage());
            }
        });
    }
}