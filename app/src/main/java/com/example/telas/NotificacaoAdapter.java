package com.example.telas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NotificacaoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Notificacao> notificacoes;

    public NotificacaoAdapter(List<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }

    @Override
    public int getItemViewType(int position) {
        return notificacoes.get(position).getTipo();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Notificacao.TIPO_TREINO || viewType == Notificacao.TIPO_SUGESTAO_TREINO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notificacao_treino, parent, false);
            return new TreinoViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notificacao_mensagem, parent, false);
            return new MensagemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Notificacao notificacao = notificacoes.get(position);
        if (holder instanceof TreinoViewHolder) {
            ((TreinoViewHolder) holder).bind(notificacao);
        } else if (holder instanceof MensagemViewHolder) {
            ((MensagemViewHolder) holder).bind(notificacao);
        }
    }

    @Override
    public int getItemCount() {
        return notificacoes.size();
    }

    class TreinoViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, conteudo, dataTreino;

        TreinoViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo_treino);
            conteudo = itemView.findViewById(R.id.conteudo_treino);
            dataTreino = itemView.findViewById(R.id.data_treino);
        }

        void bind(Notificacao notificacao) {
            if (notificacao.getTipo() == Notificacao.TIPO_TREINO) {
                titulo.setText("Próximo treino");
            } else if (notificacao.getTipo() == Notificacao.TIPO_SUGESTAO_TREINO) {
                titulo.setText("Sugestão de treino");
            }
            conteudo.setText(notificacao.getConteudo());

            if (notificacao.getDataTreino() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault());
                String dataFormatada = sdf.format(notificacao.getDataTreino());
                dataTreino.setText(dataFormatada);
            } else {
                dataTreino.setText("");
            }
        }
    }

    class MensagemViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, conteudo, detalhes;
        ImageView imagemPerfil;

        MensagemViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo_mensagem);
            conteudo = itemView.findViewById(R.id.conteudo_mensagem);
            detalhes = itemView.findViewById(R.id.detalhes_mensagem);
            imagemPerfil = itemView.findViewById(R.id.imagem_perfil);
        }

        void bind(Notificacao notificacao) {
            titulo.setText(notificacao.getTitulo());
            conteudo.setText(notificacao.getConteudo());
        }
    }
}