package com.example.telas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoricoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> listaTreinosComHeaders;

    // Construtor que recebe a lista com os itens (treinos + cabeçalhos de data)
    public HistoricoAdapter(List<Object> listaTreinosComHeaders) {
        this.listaTreinosComHeaders = listaTreinosComHeaders;
    }

    @Override
    public int getItemViewType(int position) {
        // Se o item for uma String, trata-se de um cabeçalho (data)
        if (listaTreinosComHeaders.get(position) instanceof String) {
            return 0; // Cabeçalho de data
        }
        return 1; // Item de treino
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) { // Cabeçalho de data
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_header, parent, false);
            return new HeaderViewHolder(view);
        } else { // Item de treino
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historico, parent, false);
            return new TreinoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            // Cabeçalho de data
            String data = (String) listaTreinosComHeaders.get(position);
            ((HeaderViewHolder) holder).textViewData.setText(data);
        } else if (holder instanceof TreinoViewHolder) {
            // Item de treino
            HistoricoTreino treino = (HistoricoTreino) listaTreinosComHeaders.get(position);
            ((TreinoViewHolder) holder).textViewNome.setText(treino.getNome());
            ((TreinoViewHolder) holder).textViewDescricao.setText(treino.getDescricao());
            ((TreinoViewHolder) holder).textViewAnotacao.setText(treino.getAnotacao());
        }
    }

    @Override
    public int getItemCount() {
        return listaTreinosComHeaders.size();
    }

    public void updateList(List<Object> novaLista) {
        listaTreinosComHeaders = novaLista;
        notifyDataSetChanged();
    }

    // ViewHolder para o cabeçalho (data)
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView textViewData;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            textViewData = itemView.findViewById(R.id.textViewData);
        }
    }

    // ViewHolder para o item de treino
    public static class TreinoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNome, textViewDescricao, textViewAnotacao;

        public TreinoViewHolder(View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewNome);
            textViewDescricao = itemView.findViewById(R.id.textViewDescricao);
            textViewAnotacao = itemView.findViewById(R.id.textViewAnotacao);
        }
    }
}
