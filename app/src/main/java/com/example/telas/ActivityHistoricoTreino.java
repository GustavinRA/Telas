package com.example.telas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActivityHistoricoTreino extends AppCompatActivity {

    private Button buttonAno, buttonMes;
    private RecyclerView recyclerViewHistorico;
    private HistoricoAdapter historicoAdapter;
    private int anoSelecionado = 2024;
    private int mesSelecionado = 8; // Agosto
    private List<HistoricoTreino> listaTreinos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_treino);

        buttonAno = findViewById(R.id.buttonAno);
        buttonMes = findViewById(R.id.buttonMes);
        recyclerViewHistorico = findViewById(R.id.recyclerViewHistorico);

        recyclerViewHistorico.setLayoutManager(new LinearLayoutManager(this));
        historicoAdapter = new HistoricoAdapter(new ArrayList<>());
        recyclerViewHistorico.setAdapter(historicoAdapter);

        carregarDadosMock();
        configurarFiltros();
    }

    private void configurarFiltros() {
        // Filtro de Ano
        buttonAno.setOnClickListener(v -> {
            List<Integer> anos = getAnos();
            String[] anosArray = new String[anos.size()];
            for (int i = 0; i < anos.size(); i++) {
                anosArray[i] = String.valueOf(anos.get(i));
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Selecione o Ano")
                    .setItems(anosArray, (dialog, which) -> {
                        anoSelecionado = anos.get(which);
                        buttonAno.setText(String.valueOf(anoSelecionado));
                        atualizarListaFiltrada();
                    })
                    .show();
        });

        // Filtro de Mês
        buttonMes.setOnClickListener(v -> {
            String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Selecione o Mês")
                    .setItems(meses, (dialog, which) -> {
                        mesSelecionado = which + 1; // Ajustando o mês, pois a lista começa em 0
                        buttonMes.setText(meses[which]);
                        atualizarListaFiltrada();
                    })
                    .show();
        });
    }

    private List<Integer> getAnos() {
        List<Integer> anos = new ArrayList<>();
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        int anoInicial = 2023;

        for (int ano = anoInicial; ano <= anoAtual; ano++) {
            anos.add(ano);
        }
        return anos;
    }

    private void atualizarListaFiltrada() {
        // Filtrando a lista de treinos pela data selecionada (ano e mês)
        List<Object> listaComHeaders = new ArrayList<>();
        String dataAtual = "";

        for (HistoricoTreino treino : listaTreinos) {
            // Filtra apenas os treinos que correspondem ao ano e mês selecionados
            if (treino.getAno() == anoSelecionado && treino.getMes() == mesSelecionado) {
                String dataTreino = treino.getAno() + ", " + treino.getDia() + ", " + getNomeMes(treino.getMes());
                if (!dataTreino.equals(dataAtual)) {
                    listaComHeaders.add(dataTreino); // Adiciona o cabeçalho de data
                    dataAtual = dataTreino;
                }
                listaComHeaders.add(treino); // Adiciona o treino para aquele dia
            }
        }

        historicoAdapter.updateList(listaComHeaders);
    }

    // Método para converter mês em nome
    private String getNomeMes(int mes) {
        String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        return meses[mes - 1];
    }

    private void carregarDadosMock() {
        // Dados mock de treinos
        listaTreinos.add(new HistoricoTreino("Supino Inclinado", "Descrição do supino inclinado", "Esse treino eu consegui realizar 3 séries com angulo perfeito", 2024, 8, 28));
        listaTreinos.add(new HistoricoTreino("Agachamento", "Descrição do agachamento", "Esse treino foi ótimo", 2024, 8, 28));
        listaTreinos.add(new HistoricoTreino("Remada Curvada", "Descrição da remada curvada", "Senti o movimento com boa execução", 2024, 8, 29));
        listaTreinos.add(new HistoricoTreino("Cadeira Extensora", "Descrição da cadeira extensora", "Treino para as pernas", 2024, 7, 15));

        // Exibe todos os treinos inicialmente
        atualizarListaFiltrada();
    }
}
