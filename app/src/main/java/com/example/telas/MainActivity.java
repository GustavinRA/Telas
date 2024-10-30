package com.example.telas;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.telas.api.AuthManager;

public class MainActivity extends AppCompatActivity {

    private AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authManager = new AuthManager(this);

        if(authManager.isUserLoggedIn()){
            // Verificar se o perfil existe
            authManager.verificarPerfil();
        } else {
            // Redirecionar para a tela de login
            Intent intent = new Intent(this, ActivityFormasLogin.class);
            startActivity(intent);
            finish();
        }
    }
}
