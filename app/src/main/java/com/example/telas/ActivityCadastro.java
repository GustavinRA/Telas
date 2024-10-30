package com.example.telas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.telas.api.AuthManager;

public class ActivityCadastro extends AppCompatActivity {

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
        Button buttonCadastrar = findViewById(R.id.cadastrar);

        buttonCadastrar.setOnClickListener(v -> {

            String email = editTextEmail.getText().toString().trim();
            String repetirEmail = repetirEmailEditText.getText().toString().trim();
            String senha = editTextSenha.getText().toString().trim();
            String repetirSenha = repetirSenhaEditText.getText().toString().trim();

            if(email.isEmpty() || repetirEmail.isEmpty() || senha.isEmpty() || repetirSenha.isEmpty()){
                Toast.makeText(ActivityCadastro.this, "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!email.equals(repetirEmail)){
                Toast.makeText(ActivityCadastro.this, "Os emails não correspondem", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!senha.equals(repetirSenha)){
                Toast.makeText(ActivityCadastro.this, "As senhas não correspondem", Toast.LENGTH_SHORT).show();
                return;
            }

            realizarCadastro(email, senha);
        });
    }

    private void realizarCadastro(String email, String senha){

        AuthManager authManager = new AuthManager(ActivityCadastro.this);

        authManager.realizarLogin(email, senha);
    }

    private void nextActivity(){
        finish();
        Intent intent = new Intent(ActivityCadastro.this, DadosCadastrais.class); // Alterado para dadosCadastrais
        startActivity(intent);
    }
}