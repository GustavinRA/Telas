package com.example.telas.historicoTreino;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.telas.ActivityTelaPrincipal;
import com.example.telas.R;
import com.example.telas.recyclerviewPesquisa.ActivityPesquisa;

import java.util.ArrayList;
import java.util.List;

public class ActivityHistoricoTreino extends AppCompatActivity {

    private RecyclerView recyclerViewHistorico;
    private HistoricoAdapter historicoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_treino);


        ImageButton botaoAnotacao = findViewById(R.id.btn_addAnotacao);
        botaoAnotacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityHistoricoTreino.this, CriacaoRegistroTreino.class);
                startActivity(intent);
            }
        });


        recyclerViewHistorico = findViewById(R.id.recyclerViewHistorico);
        recyclerViewHistorico.setLayoutManager(new LinearLayoutManager(this));

        historicoAdapter = new HistoricoAdapter(new ArrayList<>());
        recyclerViewHistorico.setAdapter(historicoAdapter);

        // Receber dados enviados pela Activity anterior
        String nome = getIntent().getStringExtra("nome");
        String data = getIntent().getStringExtra("data");
        String anotacao = getIntent().getStringExtra("anotacao");

        if (nome != null && data != null && anotacao != null) {
            // Criar um único item de treino e exibir
            List<Object> listaTreinos = new ArrayList<>();
            listaTreinos.add(data); // Cabeçalho de Data
            listaTreinos.add(new HistoricoTreino(nome, "Treino cadastrado", anotacao, 2024, 8, 28));
            historicoAdapter.updateList(listaTreinos);
        }
    }
}
