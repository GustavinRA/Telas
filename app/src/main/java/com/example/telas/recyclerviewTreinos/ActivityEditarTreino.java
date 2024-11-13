package com.example.telas.recyclerviewTreinos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas.R;
import com.example.telas.SharedPreferencesManager;
import com.example.telas.api.ApiClient;
import com.example.telas.api.ExerciseService;
import com.example.telas.model.Exercise;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEditarTreino extends AppCompatActivity {

    private RecyclerView recyclerViewExibicao;
    private ExercicioAdapter exercicioAdapter;
    private List<Exercise> listaExercicios;

    private Button botaoEditar;
    private LinearLayout linearCrud;
    private LinearLayout linearExibicao;

    private ExerciseService exerciseService;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_treino);

        sharedPreferencesManager = new SharedPreferencesManager(this);
        exerciseService = ApiClient.getClient().create(ExerciseService.class);

        recyclerViewExibicao = findViewById(R.id.RecyclerExibicao);
        recyclerViewExibicao.setLayoutManager(new LinearLayoutManager(this));

        listaExercicios = new ArrayList<>();

        botaoEditar = findViewById(R.id.botao_editar);
        linearCrud = findViewById(R.id.LinearCrud);
        linearExibicao = findViewById(R.id.LinearExibicao);

        linearCrud.setVisibility(View.GONE);

        botaoEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearExibicao.setVisibility(View.GONE);
                linearCrud.setVisibility(View.VISIBLE);
            }
        });

        loadExercises();
    }

    private void loadExercises() {
        String token = sharedPreferencesManager.getAuthToken();

        Call<List<Exercise>> call = exerciseService.getExercises("Bearer " + token);
        call.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                if (response.isSuccessful()) {
                    List<Exercise> exercises = response.body();
                    if (exercises != null) {
                        listaExercicios.clear();
                        listaExercicios.addAll(exercises);

                        exercicioAdapter = new ExercicioAdapter(ActivityEditarTreino.this, listaExercicios);
                        recyclerViewExibicao.setAdapter(exercicioAdapter);
                    }
                } else {
                    Toast.makeText(ActivityEditarTreino.this, "Erro ao carregar exercícios.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {
                Toast.makeText(ActivityEditarTreino.this, "Erro de rede.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}