package com.example.telas.historicoTreino;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.telas.R;

public class CriacaoRegistroTreino extends AppCompatActivity {

    private EditText editTextNome, editTextData, editTextAnotacao;
    private Button buttonSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criacao_registro_treino);

        editTextNome = findViewById(R.id.editTextNome);  // Campo Nome
        editTextData = findViewById(R.id.editTextData);  // Campo Data
        editTextAnotacao = findViewById(R.id.editTextAnotacao);  // Campo Anotação
        buttonSalvar = findViewById(R.id.buttonSalvar);

        buttonSalvar.setOnClickListener(v -> {
            String nome = editTextNome.getText().toString();
            String data = editTextData.getText().toString();
            String anotacao = editTextAnotacao.getText().toString();

            if (nome.isEmpty() || data.isEmpty() || anotacao.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // Formatar a data
                String[] dataSplit = data.split("-");
                int ano = Integer.parseInt(dataSplit[0]);
                int mes = Integer.parseInt(dataSplit[1]);
                int dia = Integer.parseInt(dataSplit[2]);

                // Mês por extenso
                String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
                String mesPorExtenso = meses[mes - 1];

                // Formatar a data como "2024, 28 agosto"
                String dataFormatada = ano + ", " + dia + " " + mesPorExtenso;

                // Enviar para a segunda Activity
                Intent intent = new Intent(CriacaoRegistroTreino.this, ActivityHistoricoTreino.class);
                intent.putExtra("nome", nome);
                intent.putExtra("data", dataFormatada);
                intent.putExtra("anotacao", anotacao);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "Formato de data inválido. Use yyyy-MM-dd.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}