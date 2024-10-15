package com.example.telas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.telas.dao.DAO;
import com.example.telas.model.Usuario;
import com.example.telas.util.Util;

public class activity_login extends AppCompatActivity {

    private EditText emailEditText;
    private EditText senhaEditText;
    private Button acessarButton;
    private Button cadastrarButton_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email);
        senhaEditText = findViewById(R.id.senha);
        acessarButton = findViewById(R.id.acessar);
        cadastrarButton_login = findViewById(R.id.cadastrar);

        acessarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEditText.getText().toString().trim();
                String senha = senhaEditText.getText().toString().trim();

                if(email.isEmpty() || senha.isEmpty()){
                    Toast.makeText(activity_login.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    String senhaMD5 = Util.converteMD5(senha);

                    Usuario usuario = new Usuario();
                    usuario.setEmail(email);
                    usuario.setSenha(senhaMD5);

                    DAO dao = new DAO(activity_login.this);
                    String resultado = dao.autenticaUsuario(usuario);

                    Toast.makeText(activity_login.this, resultado, Toast.LENGTH_SHORT).show();

                    if(resultado.equals("login efetuado com sucesso.")){
                        Intent intent = new Intent(activity_login.this, dadosCadastrais.class);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {
                    Toast.makeText(activity_login.this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        cadastrarButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Navegar para activity_cadastro
                Intent intent = new Intent(activity_login.this, activity_cadastro.class);
                startActivity(intent);

            }
        });
    }
}