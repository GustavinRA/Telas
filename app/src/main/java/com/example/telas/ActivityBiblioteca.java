package com.example.telas;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ActivityBiblioteca extends AppCompatActivity {

    private RecyclerView recyclerViewBiblioteca;
    private ExerciciosAdapter adapter;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca);

        recyclerViewBiblioteca = findViewById(R.id.recyclerBiblioteca);
        recyclerViewBiblioteca.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa o SharedPreferencesManager
        sharedPreferencesManager = new SharedPreferencesManager(this);

        // Recupera os exercícios salvos
        List<Exercicio> exerciseList = sharedPreferencesManager.getExercicios();

        if (exerciseList != null && !exerciseList.isEmpty()) {
            // Inicializa o adapter com os exercícios salvos
            adapter = new ExerciciosAdapter(this, exerciseList);
            recyclerViewBiblioteca.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Nenhum exercício encontrado", Toast.LENGTH_SHORT).show();
        }
    }
}
