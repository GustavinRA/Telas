package com.example.telas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class TreinoAdapter extends RecyclerView.Adapter<TreinoViewHolder> {

    private List<Treino> treinoList;

    public TreinoAdapter(List<Treino> treinoList) {
        this.treinoList = treinoList;
    }

    @NonNull
    @Override
    public TreinoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_treino, parent, false);
        return new TreinoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TreinoViewHolder holder, int position) {
        Treino treino = treinoList.get(position);
        holder.titulo.setText(treino.getTitulo());
        holder.descricao.setText(treino.getDescricao());

        Calendar calendario = Calendar.getInstance();
        int diaDaSemana = calendario.get(Calendar.DAY_OF_WEEK);

        String[] diasDaSemana = {"Domingo", "Segunda-feira", "Terça-feira","Quarta-feira","Quinta-feira","Sexta-feira","Sábado"};
        int diaParaExibir = (diaDaSemana - 1 + position) % 7;
        String diaAtual = diasDaSemana[diaParaExibir];
        holder.data.setText(diaAtual);
    }

    @Override
    public int getItemCount() {
        return treinoList.size();
    }

}