package com.example.telas;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActivityNotificacao extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotificacaoAdapter adapter;
    private List<Notificacao> notificacoes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao);

        recyclerView = findViewById(R.id.treino_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Cria lista de notificações para exibição
        notificacoes = criarNotificacoesExemplo();

        // Configura o adaptador
        adapter = new NotificacaoAdapter(notificacoes);
        recyclerView.setAdapter(adapter);
    }

    private List<Notificacao> criarNotificacoesExemplo() {
        List<Notificacao> lista = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        Date dataTreino = cal.getTime();
        lista.add(new Notificacao(
                Notificacao.TIPO_TREINO,
                "Próximo treino",
                "Competitiva: Glúteos e Panturrilha",
                dataTreino
        ));


        lista.add(new Notificacao(
                Notificacao.TIPO_MENSAGEM,
                "Julio César te adicionou como amigo",
                "Veja o perfil dele, veja novas possibilidades de treinos",
                null
        ));


        lista.add(new Notificacao(
                Notificacao.TIPO_SUGESTAO_TREINO,
                "Sugestão de treino",
                "Biblioteca de exercícios - Competitiva: Biceps",
                dataTreino
        ));

        return lista;
    }
}