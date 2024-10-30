package com.example.telas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.telas.api.AuthManager;

public class activity_cadastro extends AppCompatActivity {

    private Button buttonCadastrar;
    private EditText editTextEmail;
    private EditText repetirEmailEditText;
    private EditText editTextSenha;
    private EditText repetirSenhaEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editTextEmail = findViewById(R.id.email);
        repetirEmailEditText = findViewById(R.id.repetirEmail);
        editTextSenha = findViewById(R.id.senha);
        repetirSenhaEditText = findViewById(R.id.repetirSenha);
        buttonCadastrar = findViewById(R.id.cadastrar);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = editTextEmail.getText().toString().trim();
                String repetirEmail = repetirEmailEditText.getText().toString().trim();
                String senha = editTextSenha.getText().toString().trim();
                String repetirSenha = repetirSenhaEditText.getText().toString().trim();

                if(email.isEmpty() || repetirEmail.isEmpty() || senha.isEmpty() || repetirSenha.isEmpty()){
                    Toast.makeText(activity_cadastro.this, "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!email.equals(repetirEmail)){
                    Toast.makeText(activity_cadastro.this, "Os emails não correspondem", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!senha.equals(repetirSenha)){
                    Toast.makeText(activity_cadastro.this, "As senhas não correspondem", Toast.LENGTH_SHORT).show();
                    return;
                }

                realizarCadastro(email, senha);
            }
        });
    }

    private void realizarCadastro(String email, String senha){

        AuthManager authManager = new AuthManager(activity_cadastro.this);

        authManager.realizarLogin(email, senha, this::nextActivity);
    }

    private void nextActivity(){
        finish();
        Intent intent = new Intent(activity_cadastro.this, activity_tela_principal.class);
        startActivity(intent);
    }
}