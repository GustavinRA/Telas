package com.example.telas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.telas.recyclerviewTreinos.ActivityEditarTreino;

import java.util.ArrayList;
import java.util.List;

public class ActivityCriacaoExercicios extends AppCompatActivity {

    private EditText editTextNome;
    private CheckBox[] checkBoxes;  // Array de CheckBoxes
    private Button btnAdicionarTreino, treinoSalvo;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criacao_exercicios);

        // Inicializa os elementos da tela
        editTextNome = findViewById(R.id.editTextText4);
        btnAdicionarTreino = findViewById(R.id.btn_adicionarTreino);

        treinoSalvo = findViewById(R.id.salvarTreino);

        // Configuração do botão "Salvar Treino" para redirecionar para a tela de edição
        treinoSalvo.setOnClickListener(v -> {
            Intent intent3 = new Intent(ActivityCriacaoExercicios.this, ActivityEditarTreino.class);
            startActivity(intent3);
        });

        // Array de CheckBoxes
        checkBoxes = new CheckBox[]{
                findViewById(R.id.checkBox),
                findViewById(R.id.checkBox2),
                findViewById(R.id.checkBox3),
                findViewById(R.id.checkBox4),
                findViewById(R.id.checkBox5),
                findViewById(R.id.checkBox6),
                findViewById(R.id.checkBox7),
                findViewById(R.id.checkBox8),
                findViewById(R.id.checkBox9),
                findViewById(R.id.checkBox13)
        };

        // Inicializa o SharedPreferencesManager
        sharedPreferencesManager = new SharedPreferencesManager(this);

        // Evento de clique no botão "Adicionar a Treinos"
        btnAdicionarTreino.setOnClickListener(v -> {
            // Captura o nome do exercício
            String nomeExercicio = editTextNome.getText().toString();

            // Captura os checkboxes selecionados
            List<String> gruposMusculares = new ArrayList<>();
            for (CheckBox checkBox : checkBoxes) {
                if (checkBox.isChecked()) {
                    gruposMusculares.add(checkBox.getText().toString());
                }
            }

            // Validação simples
            if (nomeExercicio.isEmpty() || gruposMusculares.isEmpty()) {
                Toast.makeText(ActivityCriacaoExercicios.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Criar um novo exercício com o nome e a lista de músculos
            Exercicio novoExercicio = new Exercicio(nomeExercicio, gruposMusculares);

            // Recupera a lista de exercícios já salvos
            List<Exercicio> exercicios = sharedPreferencesManager.getExercicios();
            if (exercicios == null) {
                exercicios = new ArrayList<>();
            }

            // Adiciona o novo exercício à lista
            exercicios.add(novoExercicio);

            // Salva a lista atualizada de exercícios
            sharedPreferencesManager.saveExercicios(exercicios);

            // Exibe um feedback
            Toast.makeText(ActivityCriacaoExercicios.this, "Exercício salvo!", Toast.LENGTH_SHORT).show();

            // Limpar os campos após o salvamento
            editTextNome.setText("");
            for (CheckBox checkBox : checkBoxes) {
                checkBox.setChecked(false);
            }
        });
    }
}
