package com.example.telas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.telas.api.ApiClient;
import com.example.telas.api.AuthService;
import com.example.telas.model.LoginRequest;
import com.example.telas.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_login extends AppCompatActivity {

    private EditText emailEditText;
    private EditText senhaEditText;
    private Button acessarButton;
    private Button cadastrarButton_login;

    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email);
        senhaEditText = findViewById(R.id.senha);
        acessarButton = findViewById(R.id.acessar);
        cadastrarButton_login = findViewById(R.id.cadastrar);

        sharedPreferencesManager = new SharedPreferencesManager(this);

        acessarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEditText.getText().toString().trim();
                String senha = senhaEditText.getText().toString().trim();

                if(email.isEmpty() || senha.isEmpty()){
                    Toast.makeText(activity_login.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                realizarLogin(email, senha);
            }
        });

        cadastrarButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_login.this, activity_cadastro.class);
                startActivity(intent);
            }
        });
    }

    private void realizarLogin(String email, String senha){

        AuthService authService = ApiClient.getClient().create(AuthService.class);

        LoginRequest loginRequest = new LoginRequest(email, senha);

        Call<LoginResponse> call = authService.loginUser(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    LoginResponse loginResponse = response.body();
                    if(loginResponse != null && loginResponse.getToken() != null){
                        sharedPreferencesManager.saveAuthToken(loginResponse.getToken());

                        Toast.makeText(activity_login.this, "Login efetuado com sucesso.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity_login.this, dadosCadastrais.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(activity_login.this, "Erro ao obter token.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity_login.this, "Credenciais inválidas.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(activity_login.this, "Erro de rede: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}