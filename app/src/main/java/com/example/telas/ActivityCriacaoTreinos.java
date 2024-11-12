package com.example.telas;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ActivityCriacaoTreinos extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CriacaoTreinoAdapter adapter;
    private EditText editTextSearch;
    private List<Exercicio> exercicios;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criacao_treinos);

        // Inicializa o SharedPreferencesManager
        sharedPreferencesManager = new SharedPreferencesManager(this);

        // Inicializa os componentes da interface
        recyclerView = findViewById(R.id.recyclerView);
        editTextSearch = findViewById(R.id.editTextText5);

        // Carregar os exercícios salvos do SharedPreferences
        exercicios = sharedPreferencesManager.getExercicios();

        // Se não houver dados salvos, inicializa com uma lista padrão
        if (exercicios == null) {
            exercicios = new ArrayList<>();
        }

        // Inicializa o adapter
        adapter = new CriacaoTreinoAdapter(exercicios, this);

        // Configura o RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Listener para o campo de pesquisa
        editTextSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filtra os exercícios com base na busca
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
    }

    // Salva a lista de exercícios após alguma modificação
    public void saveExercicios() {
        sharedPreferencesManager.saveExercicios(exercicios);
    }
}
