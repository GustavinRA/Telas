package com.example.telas;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ActivityCriacaoTreinos extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CriacaoTreinoAdapter adapter;
    private EditText editTextSearch;
    private List<CriacaoTreinoExercicios> exercises;
    private boolean isEditorVisible = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criacao_treinos);

        // Inicialização dos componentes de interface
        recyclerView = findViewById(R.id.recyclerView);
        editTextSearch = findViewById(R.id.editTextText5);

        // Inicializa a lista de exercícios com alguns exemplos
        exercises = new ArrayList<>();
        exercises.add(new CriacaoTreinoExercicios("Agachamento", "Pernas, Glúteos", false));
        exercises.add(new CriacaoTreinoExercicios("Supino", "Peito, Ombros, Tríceps", false));
        exercises.add(new CriacaoTreinoExercicios("Remada", "Costas, Bíceps", false));

        // Inicializa o adapter
        adapter = new CriacaoTreinoAdapter(exercises, this);

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

    // Método para mostrar os detalhes do treino (quando o usuário clica em um exercício)
    public void showExerciseEditor(int position) {
        // Alterna a visibilidade do editor de treino
        CriacaoTreinoExercicios exercise = exercises.get(position);

        if (exercise.isAdded()) {
            Toast.makeText(this, "Exercício adicionado: " + exercise.getName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Exercício removido: " + exercise.getName(), Toast.LENGTH_SHORT).show();
        }
        // Aqui você pode fazer outras ações, como abrir o editor para edição
    }
}
