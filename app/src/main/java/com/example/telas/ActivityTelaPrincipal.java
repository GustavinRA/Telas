package com.example.telas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.telas.bibliotecaTreinos.ActivityBibliotecaTreino;
import com.example.telas.calendario.DialogSpinner;
import com.example.telas.historicoTreino.ActivityHistoricoTreino;
import com.example.telas.model.Workout;
import com.example.telas.recyclerviewPesquisa.ActivityPesquisa;
import com.example.telas.termosUso.ActivityAjuda;
import com.example.telas.termosUso.ActivityTermosUso;
import com.example.telas.user.ActivityUser;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ActivityTelaPrincipal extends AppCompatActivity implements DialogSpinner.DialogListener {

    private Button btnOpenDialog, btnRemoveTraining;
    private CalendarView calendarView;
    private String dataSelecionada; // Variável para armazenar a data selecionada
    private SharedPreferences sharedPreferences; // SharedPreferences para armazenar as informações


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


        /**
         * Botões do menu de perfil
         */

        RelativeLayout layoutUser = findViewById(R.id.MeuPerfil);
        layoutUser.setOnClickListener(v -> {
            Intent intentUser = new Intent(ActivityTelaPrincipal.this, ActivityUser.class);
            startActivity(intentUser);
        });

        RelativeLayout layoutNotificacao = findViewById(R.id.notificacao);
        layoutNotificacao.setOnClickListener(v -> {
            Intent intentNot = new Intent(ActivityTelaPrincipal.this, ActivityNotificacao.class);
            startActivity(intentNot);
        });

        RelativeLayout layoutTermos = findViewById(R.id.privacidade);
        layoutTermos.setOnClickListener(v -> {
            Intent intentTermos = new Intent(ActivityTelaPrincipal.this, ActivityTermosUso.class);
            startActivity(intentTermos);
        });

        RelativeLayout layoutAjuda = findViewById(R.id.ajuda);
        layoutAjuda.setOnClickListener(v -> {
            Intent intentAjuda = new Intent(ActivityTelaPrincipal.this, ActivityAjuda.class);
            startActivity(intentAjuda);
        });


        RelativeLayout layoutExit = findViewById(R.id.sair);
        layoutExit.setOnClickListener(v -> {
            // Limpar os dados de login no SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear(); // Limpa todos os dados salvos
            editor.apply(); // Aplica as alterações

            // Redirecionar para a tela de login
            Intent intentExit = new Intent(ActivityTelaPrincipal.this, ActivityFormasLogin.class);
            intentExit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Limpa a pilha de atividades
            startActivity(intentExit);

            // Finalizar a atividade atual (opcional, se você quiser fechar a tela principal)
            finish();
        });




        ImageButton btn_historico = findViewById(R.id.btn_historicoTreino);
        btn_historico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHistorico = new Intent(ActivityTelaPrincipal.this, ActivityHistoricoTreino.class);
                startActivity(intentHistorico);
            }
        });

        ImageButton acessarExercicioButton = findViewById(R.id.AcessarExercicio);
        acessarExercicioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityTelaPrincipal.this, ActivityBiblioteca.class);
                startActivity(intent);
            }
        });

        ImageButton acessarTreinoButton = findViewById(R.id.AcessarTreino);
        acessarTreinoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityTelaPrincipal.this, ActivityBibliotecaTreino.class);
                startActivity(intent);
            }
        });


        ImageButton botaoBuscar = findViewById(R.id.btn_buscar);
        botaoBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityTelaPrincipal.this, ActivityPesquisa.class);
                startActivity(intent);
            }
        });


        /**
         * Calendario
         */

        // Carregar os nomes dos treinos do SharedPreferences
        final List<String> treinos = loadWorkoutNames();

        // Verificar se há treinos disponíveis
        if (treinos.isEmpty()) {
            Toast.makeText(this, "Nenhum treino encontrado.", Toast.LENGTH_SHORT).show();
        } else {
            // Exibir os treinos carregados no Log ou usá-los diretamente
            for (String treino : treinos) {
                Log.d("TREINO", treino); // Apenas para verificar
            }
        }

        // Inicializando os componentes da interface
        btnOpenDialog = findViewById(R.id.btnOpenDialog);
        btnRemoveTraining = findViewById(R.id.btnRemoveTraining); // Botão para remover o treino
        calendarView = findViewById(R.id.calendarView);

        // Inicializando SharedPreferences
        sharedPreferences = getSharedPreferences("Treinos", MODE_PRIVATE); // Nome do arquivo SharedPreferences




        // Ao clicar no botão, abrir o DialogSpinner para escolher um treino
        btnOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Carregar os nomes dos treinos do SharedPreferences
                List<String> treinos = loadWorkoutNames();

                // Verificar se há treinos disponíveis
                if (treinos.isEmpty()) {
                    Toast.makeText(ActivityTelaPrincipal.this, "Nenhum treino encontrado.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Criar e exibir o DialogSpinner com a lista carregada
                DialogSpinner dialog = new DialogSpinner(treinos, ActivityTelaPrincipal.this);
                dialog.show(getSupportFragmentManager(), "DialogSpinner");
            }
        });

        // Ao clicar no botão de remover treino, remover o treino da data selecionada
        btnRemoveTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataSelecionada == null) {
                    Toast.makeText(ActivityTelaPrincipal.this, "Selecione uma data primeiro!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Remover o treino associado à data no SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(dataSelecionada); // Remove a chave (data)
                editor.apply(); // Salva as alterações

                // Exibir uma mensagem de sucesso
                Toast.makeText(ActivityTelaPrincipal.this, "Treino removido para a data " + dataSelecionada, Toast.LENGTH_SHORT).show();

                // Atualizar a interface para refletir a remoção
                btnRemoveTraining.setVisibility(View.GONE); // Esconde o botão de remover
            }
        });


        // Detectar a data selecionada no CalendarView
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Converter a data selecionada para o formato dd/MM/yyyy
            dataSelecionada = dayOfMonth + "/" + (month + 1) + "/" + year;

            // Verificar se existe um treino para essa data
            String treinoSelecionado = sharedPreferences.getString(dataSelecionada, null);

            if (treinoSelecionado != null) {
                // Exibir um Toast com a data e o treino selecionado
                String message = "Data: " + dataSelecionada + " | Treino: " + treinoSelecionado;
                Toast.makeText(ActivityTelaPrincipal.this, message, Toast.LENGTH_SHORT).show();

                // Exibir o botão de remover treino
                btnRemoveTraining.setVisibility(View.VISIBLE);
            } else {
                // Se não houver treino selecionado, pedir para o usuário escolher um treino
                Toast.makeText(ActivityTelaPrincipal.this, "Escolha um treino para essa data!", Toast.LENGTH_SHORT).show();

                // Esco nder o botão de remover treino
                btnRemoveTraining.setVisibility(View.GONE);
            }
        });
    }

    // Método chamado quando o treino é selecionado no Dialog
    @Override
    public void onTrainingSelected(String training) {
        if (dataSelecionada != null) {
            // Salvar o treino selecionado para essa data no SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(dataSelecionada, training);  // Chave: data | Valor: treino
            editor.apply(); // Salva os dados

            // Informar o usuário que o treino foi marcado
            Toast.makeText(this, "Treino '" + training + "' marcado para " + dataSelecionada, Toast.LENGTH_SHORT).show();

            // Exibir o botão de remover treino, pois agora há um treino para a data
            btnRemoveTraining.setVisibility(View.VISIBLE);
        } else {
            // Se o usuário não tiver selecionado uma data ainda
            Toast.makeText(this, "Selecione uma data primeiro!", Toast.LENGTH_SHORT).show();
        }



    }

    private List<String> loadWorkoutNames() {
        List<String> workoutNames = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("TREINOS", MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();

        Gson gson = new Gson();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String workoutJson = (String) entry.getValue();
            Workout workout = gson.fromJson(workoutJson, Workout.class);
            workoutNames.add(workout.getNome()); // Adicione apenas o nome do treino
        }

        return workoutNames;
    }

}