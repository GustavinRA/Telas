package com.example.telas.recyclerviewTreinos;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.telas.R;

import java.util.List;

public class AdicionarExercicioAdapter extends RecyclerView.Adapter<AdicionarExercicioAdapter.ViewHolder> {
    private List<Exercicio> exercicios; // Lista de exercícios disponíveis para adicionar
    private Context context;
    private onExercicioAdicionaListener listener;

    public AdicionarExercicioAdapter(List<Exercicio> exercicios, Context context, onExercicioAdicionaListener listener) {
        this.exercicios = exercicios;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adicionar_exercicio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Exercicio exercicio = exercicios.get(position);
        holder.nome.setText(exercicio.getNome());
        holder.musculosAfetados.setText(exercicio.getMusculosAfetados());

        // Quando o botão de adicionar for clicado
        holder.adicionarExercicio.setOnClickListener(v -> {
            if (listener != null) {
                listener.onExercicioAdiciona(exercicio); // Chama o método quando um item é clicado
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercicios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome, musculosAfetados;
        ImageButton adicionarExercicio;

        public ViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nome_exercicio);
            musculosAfetados = itemView.findViewById(R.id.musculos_exercicio);
            adicionarExercicio = itemView.findViewById(R.id.adicionarExercicio);
        }
    }

    public interface onExercicioAdicionaListener {
        void onExercicioAdiciona(Exercicio exercicio);
    }


}

