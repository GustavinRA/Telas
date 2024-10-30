package com.example.telas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.telas.api.AuthManager;

public class ActivityLogin extends AppCompatActivity {

    private EditText emailEditText;
    private EditText senhaEditText;
    private Button acessarButton;
    private Button cadastrarButton_login;

    private AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email);
        senhaEditText = findViewById(R.id.senha);
        acessarButton = findViewById(R.id.acessar);
        cadastrarButton_login = findViewById(R.id.cadastrar);

        authManager = new AuthManager(this); // Inicializar AuthManager

        acessarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEditText.getText().toString().trim();
                String senha = senhaEditText.getText().toString().trim();

                if(email.isEmpty() || senha.isEmpty()){
                    Toast.makeText(ActivityLogin.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                realizarLogin(email, senha);
            }
        });

        cadastrarButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityLogin.this, ActivityCadastro.class);
                startActivity(intent);
            }
        });
    }

    private void realizarLogin(String email, String senha){

        authManager.realizarLogin(email, senha);
    }
}