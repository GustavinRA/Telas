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

public class CriacaoTreinoAdapter extends RecyclerView.Adapter<CriacaoTreinoAdapter.CriacaoTreinoViewHolder> {

    private List<CriacaoTreinoExercicios> exercises;
    private Context context;

    public CriacaoTreinoAdapter(List<CriacaoTreinoExercicios> exercises, Context context) {
        this.exercises = exercises;
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

        // Exibir ou esconder os campos de edição
        if (exercise.isAdded()) {
            holder.editorTreino.setVisibility(View.VISIBLE);
            holder.btnAdd.setText("Remover");
        } else {
            holder.editorTreino.setVisibility(View.GONE);
            holder.btnAdd.setText("Adicionar");
        }

        // Configurar o botão de adicionar/remover
        holder.btnAdd.setOnClickListener(v -> {
            // Alternar a visibilidade do editor de treino
            exercise.setAdded(!exercise.isAdded());
            notifyItemChanged(position); // Atualiza a visualização
        });

        // Atualizar os campos de série, repetição e descanso
        holder.etSeries.setText(exercise.getSeries());
        holder.etRepetitions.setText(exercise.getRepetitions());
        holder.etRest.setText(exercise.getRest());

        // Salvar os dados quando os campos forem alterados
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
    public void filter(String query) {
        List<CriacaoTreinoExercicios> filteredList = new ArrayList<>();

        for (CriacaoTreinoExercicios exercise : exercises) {
            if (exercise.getName().toLowerCase().contains(query.toLowerCase()) ||
                    exercise.getMuscles().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(exercise);
            }
        }
        exercises = filteredList;
        notifyDataSetChanged();  // Notifica que a lista foi alterada
    }
}
