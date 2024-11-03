package com.example.telas;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ActivityBiblioteca extends AppCompatActivity {

    private RecyclerView recyclerViewBiblioteca;
    private ExerciciosAdapter adapter;
    private List<Exercicio> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca);

        recyclerViewBiblioteca = findViewById(R.id.recyclerBiblioteca);
        recyclerViewBiblioteca.setLayoutManager(new LinearLayoutManager(this));

        exerciseList = new ArrayList<>();

        Exercicio supinoReto = new Exercicio("Supino reto");


        Exercicio crucifixo = new Exercicio("Crucifixo máquina");


        Exercicio bicepsAlternado = new Exercicio("Bíceps alternado");


        exerciseList.add(supinoReto);
        exerciseList.add(crucifixo);
        exerciseList.add(bicepsAlternado);

        adapter = new ExerciciosAdapter(this, exerciseList);
        recyclerViewBiblioteca.setAdapter(adapter);
    }
}
