package com.example.telas;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TreinoViewHolder extends RecyclerView.ViewHolder {

    TextView titulo;
    TextView descricao;
    TextView data;

    public TreinoViewHolder(@NonNull View itemView) {
        super(itemView);
        titulo = itemView.findViewById(R.id.txtTituloTreino);
        descricao = itemView.findViewById(R.id.descricaoTreino);
        data = itemView.findViewById(R.id.txtViewDia);
    }
}

