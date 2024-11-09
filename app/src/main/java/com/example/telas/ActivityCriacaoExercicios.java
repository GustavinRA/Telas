package com.example.telas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityCriacaoExercicios extends AppCompatActivity {
    private Button btn_addTreino;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criacao_exercicios);

        btn_addTreino = findViewById(R.id.btn_adicionarTreino);

        btn_addTreino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityCriacaoExercicios.this, ActivityBibliotecaTreino.class);
                startActivity(intent);
            }
        });

    }
}