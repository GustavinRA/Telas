package com.example.telas.recyclerviewTreinos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas.R;

import java.util.List;

public class ExercicioAdapter extends RecyclerView.Adapter<ExercicioAdapter.ExercicioViewHolder> {

    private List<Exercicio> exercicios; // Lista de objetos Exercicio

    public ExercicioAdapter(List<Exercicio> exercicios) {
        this.exercicios = exercicios;
    }

    @NonNull
    @Override
    public ExercicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar o layout exibir_exercicio.xml para cada item do RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exibir_exercicio, parent, false);
        return new ExercicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExercicioViewHolder holder, int position) {
        // Associar dados do exercício aos elementos do layout
        Exercicio exercicio = exercicios.get(position);
        holder.tituloExercicio.setText(exercicio.getNome());
        // Configure os outros campos, como séries, repetições e descanso
    }

    @Override
    public int getItemCount() {
        return exercicios.size(); // Retorna o tamanho da lista de exercícios
    }

    public static class ExercicioViewHolder extends RecyclerView.ViewHolder {
        // Referência para os elementos do layout de exibir_exercicio.xml
        TextView tituloExercicio;
        EditText seriesEditText, repeticoesEditText, descansoEditText;

        public ExercicioViewHolder(View itemView) {
            super(itemView);
            tituloExercicio = itemView.findViewById(R.id.exercicio_nome_1);
            seriesEditText = itemView.findViewById(R.id.editTextSeries);
            repeticoesEditText = itemView.findViewById(R.id.editTextRepeticoes);
            descansoEditText = itemView.findViewById(R.id.editTextDescanso);
        }
    }
}
