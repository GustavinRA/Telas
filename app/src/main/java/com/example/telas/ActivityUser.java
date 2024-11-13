package com.example.telas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityUser extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        AppCompatButton buttonExercicio = findViewById(R.id.buttonExercicio);
        buttonExercicio.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityUser.this, ActivityCriacaoExercicios.class);
            startActivity(intent);
        });

        AppCompatButton buttonTreinos = findViewById(R.id.buttonTreinos);
        buttonTreinos.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityUser.this, ActivityCriacaoTreinos.class);
            startActivity(intent);
        });
    }


}