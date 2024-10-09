package com.example.telas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.telas.DAO.DAO;
import com.example.telas.OBJETOS.Usuario;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
                Intent intent = new Intent(activity_login.this,dadosCadastrais.class);
                startActivity(intent);
            }

        });

        cadastrarButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_login.this, activity_cadastro.class);
                startActivity(intent);

            }
        });

        try {
            insereUsuario("julio", "senha123");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            autenticaUsuario("julio", "senha123");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    private String converteMD5(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest codificador = MessageDigest.getInstance("MD5");
        byte[] array = codificador.digest(senha.getBytes("UTF-8"));
        StringBuffer senhaEmMD5 = new StringBuffer();
        for (int i=0; i< array.length; i++){
            senhaEmMD5.append(Integer.toHexString(array[i] & 0xFF | 0X100).substring(1,3));
        }
        return senhaEmMD5.toString();

    }

    private void autenticaUsuario(String nome, String senha) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        String senhaConvertida = converteMD5(senha);

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setSenha(senhaConvertida);

        DAO dao = new DAO(this);
        String resultado = dao.autenticaUsuario(usuario);
        Log.d("Resultado", resultado);

    }


    private void insereUsuario(String nome, String senha) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        String senhaConvertida = converteMD5(senha);

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setSenha(senhaConvertida);

        DAO dao = new DAO(this);
        String resultado = dao.insereUsuario(usuario);
        Log.d("Resultado ", resultado);

    }
    }
