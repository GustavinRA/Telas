package com.example.telas.recyclerviewTreinos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.telas.R;

import java.util.List;


public class EditarExercicioAdapter extends RecyclerView.Adapter<EditarExercicioAdapter.ViewHolder> {
    private List<Exercicio> exercicios;
    private Context context;

    public EditarExercicioAdapter(List<Exercicio> exercicios, Context context) {
        this.exercicios = exercicios;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.editar_exercicio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Exercicio exercicio = exercicios.get(position);
        holder.nome.setText(exercicio.getNome());
        // Associe as edições de séries, repetições e descanso aqui.
    }

    @Override
    public int getItemCount() {

        return exercicios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome;
        EditText series, repeticoes, descanso;

        public ViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.exercicio_nome_1);
            series = itemView.findViewById(R.id.series);
            repeticoes = itemView.findViewById(R.id.repeticoes);
            descanso = itemView.findViewById(R.id.descanso);
        }
    }
}
