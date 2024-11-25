package com.example.telas.bibliotecaTreinos;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas.R;
import com.example.telas.model.WorkoutExercise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AdapterExercise extends RecyclerView.Adapter<AdapterExercise.ExerciseViewHolder> {
    private List<WorkoutExercise> workoutExercises;
    private Map<Integer, Boolean> expandedState = new HashMap<>();

    public AdapterExercise(List<WorkoutExercise> workoutExercises) {
        this.workoutExercises = workoutExercises;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_repeticao, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        WorkoutExercise workoutExercise = workoutExercises.get(position);

        // Nome do exercício
        String nomeExercicio = workoutExercise.getExercise().getNome();
        Log.d("AdapterExercise", "Nome do exercício: " + nomeExercicio);
        holder.tvExerciseName2.setText(nomeExercicio != null ? nomeExercicio : "Nome não disponível");


        // Musculatura afetada (convertendo Set<String> para String)
        Set<String> muscles = workoutExercise.getExercise().getMusculosAfetados();
        if (muscles != null && !muscles.isEmpty()) {
            holder.tvMusclesAffected2.setText("Músculos: " + String.join(", ", muscles)); // Concatenando os músculos separados por vírgula
        } else {
            holder.tvMusclesAffected2.setText("Músculos: Não especificado");
        }




        // Séries, repetições e descanso
        holder.tvSets.setText("Séries: " + workoutExercise.getSeries());
        holder.tvReps.setText("Repetições: " + workoutExercise.getRepetitions());
        holder.tvRest.setText("Descanso: " + workoutExercise.getRest());

        // Detalhes visíveis ou não
        boolean isExpanded = expandedState.getOrDefault(position, false);
        holder.llExerciseDetails.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        // Lógica de clique para expandir/ocultar detalhes
        holder.itemView.setOnClickListener(v -> {
            expandedState.put(position, !isExpanded);
            notifyItemChanged(position);
        });
    }


    @Override
    public int getItemCount() {
        return workoutExercises.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView tvExerciseName2, tvMusclesAffected2, tvSets, tvReps, tvRest;
        LinearLayout llExerciseDetails;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);

            tvExerciseName2 = itemView.findViewById(R.id.tvExerciseName2);
            tvMusclesAffected2 = itemView.findViewById(R.id.tvAffectedMuscles2);
            llExerciseDetails = itemView.findViewById(R.id.llExerciseDetails);
            tvSets = itemView.findViewById(R.id.tvSets);
            tvReps = itemView.findViewById(R.id.tvReps);
            tvRest = itemView.findViewById(R.id.tvRest);
        }
    }
}
