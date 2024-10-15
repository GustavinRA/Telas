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

        // Inicialização dos campos com os IDs corretos
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

                try {
                    String senhaMD5 = Util.converteMD5(senha);

                    Usuario usuario = new Usuario();
                    usuario.setEmail(email);
                    usuario.setSenha(senhaMD5);

                    DAO dao = new DAO(activity_cadastro.this);
                    String resultado = dao.insereUsuario(usuario);

                    if (resultado.equals("Sucesso ao cadastrar o usuário")) {
                        Toast.makeText(activity_cadastro.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity_cadastro.this, activity_login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(activity_cadastro.this, resultado, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(activity_cadastro.this, "Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
}