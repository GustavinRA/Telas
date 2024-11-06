package com.example.telas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView; // Import necessário para TextView

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityTelaPrincipal extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal); // Certifique-se de que o nome do layout está correto

        // Inicializar SharedPreferencesManager
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(this);

        // Inicializar TextView
        // Alterado para TextView
        TextView txtNomeP = findViewById(R.id.txtNomeP);

        // Recuperar o nome do usuário salvo nas preferências
        String username = sharedPreferencesManager.getUsername();
        String greeting = getString(R.string.txtNomeP, username);
        txtNomeP.setText(greeting); // Agora funciona porque txtNomeP é um TextView

        // Inicializar LineChart
        LineChart linechart = findViewById(R.id.lineChart);

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 1));
        entries.add(new Entry(1, 3));
        entries.add(new Entry(2, 2));
        entries.add(new Entry(3, 5));

        LineDataSet dataSet = new LineDataSet(entries, "Valores");
        dataSet.setColor(getResources().getColor(android.R.color.holo_blue_dark));
        dataSet.setValueTextColor(getResources().getColor(android.R.color.black));

        LineData lineData = new LineData(dataSet);
        linechart.setData(lineData);
        linechart.invalidate();

        // Criar exemplos de Treino
        Treino treino1 = new Treino();
        treino1.setData(new Date());
        treino1.setTitulo("Peito");
        treino1.setDescricao("Treino com foco em peito e tríceps");

        Treino treino2 = new Treino();
        treino2.setTitulo("Costas");
        treino2.setDescricao("Treino com foco em costas e bíceps");
        treino2.setData(new Date());

        Treino treino3 = new Treino();
        treino3.setTitulo("Perna");
        treino3.setDescricao("Treino focado em pernas");
        treino3.setData(new Date());

        List<Treino> treinoList = new ArrayList<>();
        treinoList.add(treino1);
        treinoList.add(treino2);
        treinoList.add(treino3);

        // Configurar RecyclerView
        RecyclerView treinoRecyclerView = findViewById(R.id.treino_recycler_view);
        treinoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        TreinoAdapter adapter = new TreinoAdapter(treinoList);
        treinoRecyclerView.setAdapter(adapter);



        // Inicializar o botão de perfil e o menu oculto
        ImageButton perfilButton = findViewById(R.id.perfil);
        LinearLayout hiddenMenu = findViewById(R.id.linearLayout);

        // Configurar visibilidade do menu
        hiddenMenu.setVisibility(View.GONE); // Oculto por padrão

        perfilButton.setOnClickListener(v -> {
            // Alternar a visibilidade do menu
            if (hiddenMenu.getVisibility() == View.GONE) {
                hiddenMenu.setVisibility(View.VISIBLE);
            } else {
                hiddenMenu.setVisibility(View.GONE);
            }
        });




        RelativeLayout layoutNotificacao = findViewById(R.id.notificacao);
        layoutNotificacao.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityTelaPrincipal.this, ActivityNotificacao.class);
            startActivity(intent);
        });


        RelativeLayout layoutUser = findViewById(R.id.MeuPerfil);
        layoutUser.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityTelaPrincipal.this, ActivityUser.class);
            startActivity(intent);
        });
    }
}