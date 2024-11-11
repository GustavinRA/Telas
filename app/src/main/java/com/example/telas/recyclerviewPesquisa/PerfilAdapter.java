package com.example.telas.recyclerviewPesquisa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas.R;

import java.util.List;

public class PerfilAdapter extends RecyclerView.Adapter<PerfilAdapter.PerfilViewHolder> {

    private List<UsuarioPerfil> listaPerfis;
    private Context context;

    public PerfilAdapter(List<UsuarioPerfil> listaPerfis, Context context) {
        this.listaPerfis = listaPerfis;
        this.context = context;
    }

    @NonNull
    @Override
    public PerfilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pesquisa_amigo, parent, false);
        return new PerfilViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PerfilViewHolder holder, int position) {
        UsuarioPerfil perfil = listaPerfis.get(position);
        holder.nomeUsuario.setText(perfil.getNome());
        holder.imagemPerfil.setImageResource(perfil.getImagemResId());

        holder.botaoFechar.setOnClickListener(v -> {
            // Lógica para fechar o card ou remover o item da lista (opcional)
            listaPerfis.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, listaPerfis.size());
        });
    }

    @Override
    public int getItemCount() {
        return listaPerfis.size();
    }

    static class PerfilViewHolder extends RecyclerView.ViewHolder {
        ImageView imagemPerfil;
        TextView nomeUsuario;
        ImageButton botaoFechar;

        public PerfilViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemPerfil = itemView.findViewById(R.id.imagem_perfil);
            nomeUsuario = itemView.findViewById(R.id.nome_usuario);
            botaoFechar = itemView.findViewById(R.id.botao_fechar);
        }
    }
}
