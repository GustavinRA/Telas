package com.example.telas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.telas.api.ApiClient;
import com.example.telas.api.ExerciseService;
import com.example.telas.model.Exercise;

import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCriacaoExercicios extends AppCompatActivity {

    private EditText editTextExerciseName;
    private Button btnSaveExercise;
    private SharedPreferencesManager sharedPreferencesManager;

    private CheckBox checkBoxPeito;
    private CheckBox checkBoxCostas;
    private CheckBox checkBoxTriceps;
    private CheckBox checkBoxBiceps;
    private CheckBox checkBoxAbdomen;
    private CheckBox checkBoxQuadriceps;
    private CheckBox checkBoxPanturrilha;
    private CheckBox checkBoxOmbro;
    private CheckBox checkBoxGluteos;
    private CheckBox checkBoxPosteriorCoxa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criacao_exercicios);

        sharedPreferencesManager = new SharedPreferencesManager(this);

        editTextExerciseName = findViewById(R.id.editTextText4);
        btnSaveExercise = findViewById(R.id.salvarTreino); // Botão "Salvar!"

        checkBoxPeito = findViewById(R.id.checkBox); // Peito
        checkBoxCostas = findViewById(R.id.checkBox2); // Costas
        checkBoxTriceps = findViewById(R.id.checkBox3); // Tríceps
        checkBoxBiceps = findViewById(R.id.checkBox4); // Bíceps
        checkBoxAbdomen = findViewById(R.id.checkBox5); // Abdômen
        checkBoxQuadriceps = findViewById(R.id.checkBox6); // Quadríceps
        checkBoxPanturrilha = findViewById(R.id.checkBox7); // Panturrilha
        checkBoxOmbro = findViewById(R.id.checkBox8); // Ombro
        checkBoxGluteos = findViewById(R.id.checkBox9); // Glúteos
        checkBoxPosteriorCoxa = findViewById(R.id.checkBox13); // Posterior de coxa

        btnSaveExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveExercise();
            }
        });

        Button buttonReturn = findViewById(R.id.button);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    /**
     * Método para salvar o exercício no back-end.
     */
    private void saveExercise() {

        String exerciseName = editTextExerciseName.getText().toString().trim();
        if (exerciseName.isEmpty()) {
            Toast.makeText(this, "Por favor, insira um nome para o exercício.", Toast.LENGTH_SHORT).show();
            return;
        }

        Set<String> selectedMuscles = new HashSet<>();

        if (checkBoxPeito.isChecked()) {
            selectedMuscles.add("Peito");
        }
        if (checkBoxCostas.isChecked()) {
            selectedMuscles.add("Costas");
        }
        if (checkBoxTriceps.isChecked()) {
            selectedMuscles.add("Tríceps");
        }
        if (checkBoxBiceps.isChecked()) {
            selectedMuscles.add("Bíceps");
        }
        if (checkBoxAbdomen.isChecked()) {
            selectedMuscles.add("Abdômen");
        }
        if (checkBoxQuadriceps.isChecked()) {
            selectedMuscles.add("Quadríceps");
        }
        if (checkBoxPanturrilha.isChecked()) {
            selectedMuscles.add("Panturrilha");
        }
        if (checkBoxOmbro.isChecked()) {
            selectedMuscles.add("Ombro");
        }
        if (checkBoxGluteos.isChecked()) {
            selectedMuscles.add("Glúteos");
        }
        if (checkBoxPosteriorCoxa.isChecked()) {
            selectedMuscles.add("Posterior de coxa");
        }

        if (selectedMuscles.isEmpty()) {
            Toast.makeText(this, "Por favor, selecione pelo menos um grupo muscular.", Toast.LENGTH_SHORT).show();
            return;
        }

        Exercise exercise = new Exercise();
        exercise.setNome(exerciseName);
        exercise.setMusculosAfetados(selectedMuscles);

        ExerciseService exerciseService = ApiClient.getClient().create(ExerciseService.class);
        String token = sharedPreferencesManager.getAuthToken();

        Call<Exercise> call = exerciseService.createExercise("Bearer " + token, exercise);
        call.enqueue(new Callback<Exercise>() {
            @Override
            public void onResponse(Call<Exercise> call, Response<Exercise> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ActivityCriacaoExercicios.this, "Exercício criado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ActivityCriacaoExercicios.this, "Erro ao criar exercício.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Exercise> call, Throwable t) {
                Toast.makeText(ActivityCriacaoExercicios.this, "Erro de rede.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}