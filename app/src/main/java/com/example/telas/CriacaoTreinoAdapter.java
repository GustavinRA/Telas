package com.example.telas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CriacaoTreinoAdapter extends RecyclerView.Adapter<CriacaoTreinoAdapter.CriacaoTreinoViewHolder> {

    private List<Exercicio> exercicios;  // Lista de objetos Exercicio
    private Context context;

    public CriacaoTreinoAdapter(List<Exercicio> exercicios, Context context) {
        this.exercicios = exercicios;
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
        Exercicio exercicio = exercicios.get(position);

        holder.tvExerciseName.setText(exercicio.getNome());
        holder.tvMuscles.setText(exercicio.getMusculosComoTexto());

        // Ação para o botão "Adicionar" ou "Remover"
        if (exercicio.isAdded()) {
            holder.btnAdd.setText("Remover");
            holder.editorTreino.setVisibility(View.VISIBLE);  // Torna visível os campos de edição
        } else {
            holder.btnAdd.setText("Adicionar");
            holder.editorTreino.setVisibility(View.GONE);  // Torna invisível os campos de edição
        }

        // Configura o botão de adicionar/remover
        holder.btnAdd.setOnClickListener(v -> {
            // Alterna o estado "adicionado/removido"
            exercicio.setAdded(!exercicio.isAdded());

            // Notifica a mudança de item
            notifyItemChanged(position);

            // Exibe a visibilidade do editor de treino dependendo do estado
            if (exercicio.isAdded()) {
                holder.editorTreino.setVisibility(View.VISIBLE);
                Toast.makeText(context, "Exercício adicionado: " + exercicio.getNome(), Toast.LENGTH_SHORT).show();
            } else {
                holder.editorTreino.setVisibility(View.GONE);
                Toast.makeText(context, "Exercício removido: " + exercicio.getNome(), Toast.LENGTH_SHORT).show();
            }
        });

        // Define os listeners de foco para os campos de séries, repetições e descanso
        holder.etSeries.setText(exercicio.getSeries());  // Configura o valor de Séries
        holder.etRepetitions.setText(exercicio.getRepetitions());  // Configura o valor de Repetições
        holder.etRest.setText(exercicio.getRest());  // Configura o valor de Descanso

        // Atualiza o valor de 'Séries' quando o campo perde o foco
        holder.etSeries.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                exercicio.setSeries(holder.etSeries.getText().toString());
            }
        });

        // Atualiza o valor de 'Repetições' quando o campo perde o foco
        holder.etRepetitions.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                exercicio.setRepetitions(holder.etRepetitions.getText().toString());
            }
        });

        // Atualiza o valor de 'Descanso' quando o campo perde o foco
        holder.etRest.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                exercicio.setRest(holder.etRest.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercicios.size();
    }

    public static class CriacaoTreinoViewHolder extends RecyclerView.ViewHolder {

        TextView tvExerciseName, tvMuscles;
        Button btnAdd;
        EditText etSeries, etRepetitions, etRest;  // Declare os EditTexts para Série, Repetições e Descanso
        LinearLayout editorTreino;  // LinearLayout que contém os campos de edição

        public CriacaoTreinoViewHolder(View itemView) {
            super(itemView);
            tvExerciseName = itemView.findViewById(R.id.tvExerciseName);
            tvMuscles = itemView.findViewById(R.id.tvAffectedMuscles);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            etSeries = itemView.findViewById(R.id.etSeries);
            etRepetitions = itemView.findViewById(R.id.etRepetitions);
            etRest = itemView.findViewById(R.id.etRest);
            editorTreino = itemView.findViewById(R.id.editorTreino);
        }
    }

    // Método para filtrar a lista de exercícios com base na pesquisa
    public void filter(String query) {
        List<Exercicio> filteredList = new ArrayList<>();

        for (Exercicio exercicio : exercicios) {
            if (exercicio.getNome().toLowerCase().contains(query.toLowerCase()) ||
                    exercicio.getMusculosComoTexto().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(exercicio);
            }
        }
        exercicios = filteredList;
        notifyDataSetChanged();  // Notifica que a lista foi alterada
    }
}
