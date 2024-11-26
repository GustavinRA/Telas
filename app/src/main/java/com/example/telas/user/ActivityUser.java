package com.example.telas.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.telas.ActivityCriacaoExercicios;
import com.example.telas.ActivityCriacaoTreinos;
import com.example.telas.R;

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