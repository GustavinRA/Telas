package com.example.telas.recyclerviewTreinos;

import android.content.Context;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas.R;
import com.example.telas.SharedPreferencesManager;
import com.example.telas.api.ApiClient;
import com.example.telas.api.ExerciseService;
import com.example.telas.model.Exercise;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Adapter para a RecyclerView de exercícios na ActivityBiblioteca.
 */
public class ExercicioAdapter extends RecyclerView.Adapter<ExercicioAdapter.ViewHolder> {

    private final List<Exercise> exerciseList;
    private final Context context;
    private ExerciseService exerciseService;
    private SharedPreferencesManager sharedPreferencesManager;

    public ExercicioAdapter(Context context, List<Exercise> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
        this.exerciseService = ApiClient.getClient().create(ExerciseService.class);
        this.sharedPreferencesManager = new SharedPreferencesManager(context);
    }

    @NonNull
    @Override
    public ExercicioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_exercicios, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExercicioAdapter.ViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        holder.exerciseName.setText(exercise.getNome());
        holder.affectedMuscles.setText("Músculos afetados: " + String.join(", ", exercise.getMusculosAfetados()));

        holder.checkboxContainer.setVisibility(View.GONE);
        holder.exerciseName.setEnabled(false);

        holder.editButton.setOnClickListener(v -> {
            holder.checkboxContainer.setVisibility(View.VISIBLE);
            holder.exerciseName.setEnabled(true);
            setupCheckboxes(exercise, holder);
        });

        holder.saveButton.setOnClickListener(v -> {
            updateExerciseInBackend(exercise, holder);
        });
    }

    /**
     * Configura os checkboxes e seus listeners.
     */
    private void setupCheckboxes(Exercise exercise, ViewHolder holder) {
        CheckBox[] checkboxes = new CheckBox[]{
                holder.checkboxPeito,
                holder.checkboxCostas,
                holder.checkboxTriceps,
                holder.checkboxBiceps,
                holder.checkboxAbdomen,
                holder.checkboxQuadriceps,
                holder.checkboxPanturrilha,
                holder.checkboxOmbro,
                holder.checkboxGluteos
        };

        String[] muscles = new String[]{
                "Peito", "Costas", "Tríceps", "Bíceps", "Abdômen",
                "Quadríceps", "Panturrilha", "Ombro", "Glúteos"
        };

        List<String> selectedMuscles = new ArrayList<>(exercise.getMusculosAfetados());
        exercise.setMusculosAfetados(selectedMuscles);

        for (int i = 0; i < checkboxes.length; i++) {
            CheckBox checkbox = checkboxes[i];
            String muscle = muscles[i];

            checkbox.setChecked(exercise.getMusculosAfetados().contains(muscle));

            checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    exercise.getMusculosAfetados().add(muscle);
                } else {
                    exercise.getMusculosAfetados().remove(muscle);
                }
                holder.affectedMuscles.setText("Músculos afetados: " + String.join(", ", exercise.getMusculosAfetados()));
            });
        }

        holder.exerciseName.setEnabled(true);
        holder.exerciseName.setText(exercise.getNome());
        holder.exerciseName.addTextChangedListener(new android.text.TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                exercise.setNome(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
            }
        });
    }

    /**
     * Atualiza o exercício no back-end.
     */
    private void updateExerciseInBackend(Exercise exercise, ViewHolder holder) {
        String token = sharedPreferencesManager.getAuthToken();
        Call<Exercise> call = exerciseService.updateExercise("Bearer " + token, exercise.getExerciseId(), exercise);

        call.enqueue(new Callback<Exercise>() {
            @Override
            public void onResponse(@NonNull Call<Exercise> call, @NonNull Response<Exercise> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Exercício atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    holder.checkboxContainer.setVisibility(View.GONE);
                    holder.exerciseName.setEnabled(false);
                } else {
                    Toast.makeText(context, "Erro ao atualizar exercício.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Exercise> call, @NonNull Throwable t) {
                Toast.makeText(context, "Erro de rede ao atualizar exercício.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        EditText exerciseName;
        TextView affectedMuscles;
        Button editButton;
        LinearLayout checkboxContainer;
        Button saveButton;

        CheckBox checkboxPeito;
        CheckBox checkboxCostas;
        CheckBox checkboxTriceps;
        CheckBox checkboxBiceps;
        CheckBox checkboxAbdomen;
        CheckBox checkboxQuadriceps;
        CheckBox checkboxPanturrilha;
        CheckBox checkboxOmbro;
        CheckBox checkboxGluteos;

        ViewHolder(View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exercicio_nome);
            affectedMuscles = itemView.findViewById(R.id.musculoAfetadoId);
            editButton = itemView.findViewById(R.id.edit_button);
            checkboxContainer = itemView.findViewById(R.id.checkbox_container);
            saveButton = itemView.findViewById(R.id.save_button);

            checkboxPeito = itemView.findViewById(R.id.checkbox_peito);
            checkboxCostas = itemView.findViewById(R.id.checkbox_costas);
            checkboxTriceps = itemView.findViewById(R.id.checkbox_triceps);
            checkboxBiceps = itemView.findViewById(R.id.checkbox_biceps);
            checkboxAbdomen = itemView.findViewById(R.id.checkbox_abdomen);
            checkboxQuadriceps = itemView.findViewById(R.id.checkbox_quadriceps);
            checkboxPanturrilha = itemView.findViewById(R.id.checkbox_panturrilha);
            checkboxOmbro = itemView.findViewById(R.id.checkbox_ombro);
            checkboxGluteos = itemView.findViewById(R.id.checkbox_gluteos);
        }
    }
}