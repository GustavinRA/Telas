package com.example.telas.recyclerviewTreinos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas.ActivityCriacaoTreinos;
import com.example.telas.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityEditarTreino extends AppCompatActivity {

    private RecyclerView recyclerViewExibicao, recyclerViewRemover, recyclerViewAdicionar, recyclerViewEditar;
    private ExercicioAdapter exercicioAdapter;
    private ExcluirExercicioAdapter excluirAdapter;
    private List<Exercicio> listaExercicios;
    private List<Exercicio> listaExerciciosRemover;
    private List<Exercicio> listaExerciciosAdicionar;
    private List<Exercicio> listaExerciciosEditar;
    private AdicionarExercicioAdapter adicionarExercicioAdapter;

    private ImageButton btn_treino;

    // Referência para os elementos do layout
    private Button botaoEditar;
    private LinearLayout linearCrud;
    private LinearLayout linearExibicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_treino);

        btn_treino = findViewById(R.id.btn_editarTreino);
        btn_treino.setOnClickListener(new View.OnClickListener() {

            @Override
          public void onClick(View v) {
                Intent intent4 = new Intent(ActivityEditarTreino.this, ActivityCriacaoTreinos.class);
                startActivity(intent4);
            }
      });

        // Inicializando os RecyclerViews
        recyclerViewExibicao = findViewById(R.id.RecyclerExibicao);
        recyclerViewRemover = findViewById(R.id.RecyclerRemover);
        recyclerViewAdicionar = findViewById(R.id.RecyclerAdicionar);
        recyclerViewEditar = findViewById(R.id.RecyclerEditar);

        // Configuração do LayoutManager para RecyclerViews
        recyclerViewExibicao.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRemover.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdicionar.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEditar.setLayoutManager(new LinearLayoutManager(this));

        // Criar lista de exercícios
        listaExercicios = new ArrayList<>();
        listaExercicios.add(new Exercicio("Exercício A", 4, 10, "2:00"));



        // Criar listas de exercícios para os outros RecyclerViews
        listaExerciciosRemover = new ArrayList<>(listaExercicios);
        listaExerciciosAdicionar = new ArrayList<>(listaExercicios);
        listaExerciciosEditar = new ArrayList<>(listaExercicios);

        // Configurar o Adapter para o RecyclerView Exibicao
        exercicioAdapter = new ExercicioAdapter(listaExercicios);
        recyclerViewExibicao.setAdapter(exercicioAdapter);

        // Configurar os Adapters para os outros RecyclerViews (Remover, Adicionar, Editar)
        ExercicioAdapter removerAdapter = new ExercicioAdapter(listaExerciciosRemover);
        ExercicioAdapter adicionarAdapter = new ExercicioAdapter(listaExerciciosAdicionar);
        recyclerViewAdicionar.setAdapter(adicionarAdapter);
        recyclerViewRemover.setAdapter(removerAdapter);



        ExercicioAdapter editarAdapter = new ExercicioAdapter(listaExerciciosEditar);
        recyclerViewEditar.setAdapter(editarAdapter);

        // Configurar Adapter para Excluir Exercício
        excluirAdapter = new ExcluirExercicioAdapter(listaExerciciosRemover, this, new ExcluirExercicioAdapter.OnExercicioRemovedListener() {
            @Override
            public void onExercicioRemoved(Exercicio exercicio) {
                // Atualiza a lista de exibição e remover
                listaExercicios.remove(exercicio);
                listaExerciciosRemover.remove(exercicio);
                exercicioAdapter.notifyDataSetChanged();
            }
        });
        recyclerViewRemover.setAdapter(excluirAdapter);

        // Inicializando corretamente a variável adicionarExercicioAdapter
        adicionarExercicioAdapter = new AdicionarExercicioAdapter(listaExerciciosAdicionar, this, new AdicionarExercicioAdapter.onExercicioAdicionaListener() {
            @Override
            public void onExercicioAdiciona(Exercicio exercicio) {
                // Adiciona o exercício na lista de exercícios do treino
                listaExercicios.add(exercicio);
                // Remove o exercício da lista de adicionar
                listaExerciciosAdicionar.add(exercicio);
                // Notifica os adapters para atualizar a UI
                exercicioAdapter.notifyDataSetChanged();
            }
        });

        recyclerViewAdicionar.setAdapter(adicionarExercicioAdapter);



        // Inicializando os elementos do layout
        botaoEditar = findViewById(R.id.botao_editar);
        linearCrud = findViewById(R.id.LinearCrud);
        linearExibicao = findViewById(R.id.LinearExibicao);

        // Tornar o LinearCrud invisível inicialmente
        linearCrud.setVisibility(View.GONE);

        // Configurar evento de clique para o botão "Editar"
        botaoEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Esconder o LinearExibicao
                linearExibicao.setVisibility(View.GONE);
                // Tornar o LinearCrud visível
                linearCrud.setVisibility(View.VISIBLE);
            }
        });


    }
}

