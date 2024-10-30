package com.example.telas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class activity_tela_principal extends AppCompatActivity {
   private RecyclerView treinoRecyclerView;
   private LineChart linechart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

// ewe

        linechart = findViewById(R.id.lineChart);

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

        Treino treino1 = new Treino();
        treino1.setData(new Date());
        treino1.setTitulo("Peito");
        treino1.setDescricao("Treino com foco em peito e tríceps");

        Treino treino2 = new Treino();
        treino2.setTitulo("Costas");
        treino2.setDescricao("Treino com foco em costas e biceps");
        treino2.setData(new Date());

        Treino treino3 = new Treino();
        treino3.setTitulo("Perna");
        treino3.setDescricao("Treino focado em pernas");
        treino3.setData(new Date());


        List<Treino> treinoList = new ArrayList<>();
        treinoList.add(treino1);
          treinoList.add(treino2);
          treinoList.add(treino3);

        treinoRecyclerView = findViewById(R.id.treino_recycler_view);
        treinoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        TreinoAdapter adapter = new TreinoAdapter(treinoList);
        treinoRecyclerView.setAdapter(adapter);

        Button treino = findViewById(R.id.treino);

        treino.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_tela_principal.this, definir_treinos.class);
                startActivity(intent);
            }
        });


    }


}