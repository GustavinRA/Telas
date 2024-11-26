package com.example.telas.historicoTreino;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas.R;

import java.util.List;

public class HistoricoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> listaTreinosComHeaders;

    public HistoricoAdapter(List<Object> listaTreinosComHeaders) {
        this.listaTreinosComHeaders = listaTreinosComHeaders;
    }

    @Override
    public int getItemViewType(int position) {
        if (listaTreinosComHeaders.get(position) instanceof String) {
            return 0; // Cabeçalho de Data
        }
        return 1; // Item de Treino
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historico, parent, false);
            return new TreinoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            String data = (String) listaTreinosComHeaders.get(position);
            ((HeaderViewHolder) holder).textViewData.setText(data);
        } else if (holder instanceof TreinoViewHolder) {
            HistoricoTreino treino = (HistoricoTreino) listaTreinosComHeaders.get(position);
            ((TreinoViewHolder) holder).textViewNome.setText(treino.getNome());
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

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView textViewData;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            textViewData = itemView.findViewById(R.id.textViewData);
        }
    }

    public static class TreinoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNome, textViewDescricao, textViewAnotacao;

        public TreinoViewHolder(View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewNome);
            textViewAnotacao = itemView.findViewById(R.id.textViewAnotacao);
        }
    }
}
