package com.example.telas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter para a RecyclerView na ActivityCriacaoTreinos.
 */
public class CriacaoTreinoAdapter extends RecyclerView.Adapter<CriacaoTreinoAdapter.CriacaoTreinoViewHolder> {

    private List<CriacaoTreinoExercicios> exercises;
    private List<CriacaoTreinoExercicios> exercisesFull;
    private Context context;

    public CriacaoTreinoAdapter(List<CriacaoTreinoExercicios> exercises, Context context) {
        this.exercises = new ArrayList<>(exercises);
        this.exercisesFull = new ArrayList<>(exercises);
        this.context = context;
    }

    @NonNull
    @Override
    public CriacaoTreinoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_criacao_treino, parent, false);
        return new CriacaoTreinoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CriacaoTreinoViewHolder holder, int position) {
        CriacaoTreinoExercicios exercise = exercises.get(position);

        holder.tvExerciseName.setText(exercise.getName());
        holder.tvMuscles.setText(exercise.getMuscles());

        if (exercise.isAdded()) {
            holder.editorTreino.setVisibility(View.VISIBLE);
            holder.btnAdd.setText("Remover");
        } else {
            holder.editorTreino.setVisibility(View.GONE);
            holder.btnAdd.setText("Adicionar");
        }

        holder.btnAdd.setOnClickListener(v -> {
            exercise.setAdded(!exercise.isAdded());
            notifyItemChanged(position);
        });

        holder.etSeries.setText(exercise.getSeries());
        holder.etRepetitions.setText(exercise.getRepetitions());
        holder.etRest.setText(exercise.getRest());

        holder.etSeries.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                exercise.setSeries(holder.etSeries.getText().toString());
            }
        });

        holder.etRepetitions.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                exercise.setRepetitions(holder.etRepetitions.getText().toString());
            }
        });

        holder.etRest.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                exercise.setRest(holder.etRest.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class CriacaoTreinoViewHolder extends RecyclerView.ViewHolder {

        TextView tvExerciseName, tvMuscles;
        Button btnAdd;
        LinearLayout editorTreino;
        EditText etSeries, etRepetitions, etRest;

        public CriacaoTreinoViewHolder(View itemView) {
            super(itemView);
            tvExerciseName = itemView.findViewById(R.id.tvExerciseName);
            tvMuscles = itemView.findViewById(R.id.tvAffectedMuscles);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            editorTreino = itemView.findViewById(R.id.editorTreino);
            etSeries = itemView.findViewById(R.id.etSeries);
            etRepetitions = itemView.findViewById(R.id.etRepetitions);
            etRest = itemView.findViewById(R.id.etRest);
        }
    }

    /**
     * Filtra a lista de exercícios com base na query.
     */
    public void filter(String query) {
        exercises.clear();
        if (query == null || query.isEmpty()) {
            exercises.addAll(exercisesFull);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (CriacaoTreinoExercicios exercise : exercisesFull) {
                if (exercise.getName().toLowerCase().contains(lowerCaseQuery) ||
                        exercise.getMuscles().toLowerCase().contains(lowerCaseQuery)) {
                    exercises.add(exercise);
                }
            }
        }
        notifyDataSetChanged();
    }
}