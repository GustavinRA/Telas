package com.example.telas.api;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.telas.SharedPreferencesManager;
import com.example.telas.dadosCadastrais;
import com.example.telas.model.LoginRequest;
import com.example.telas.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthManager {
    private Context context;
    private SharedPreferencesManager sharedPreferencesManager;

    public AuthManager(Context context) {
        this.context = context;
        this.sharedPreferencesManager = new SharedPreferencesManager(context);
    }

    /**
     * Realiza o login do usuário com email e senha.
     *
     * @param email    O email do usuário.
     * @param senha    A senha do usuário.
     * @param callback Um callback opcional para ações adicionais após o login.
     */
    public void realizarLogin(String email, String senha, final Runnable callback) {
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

                        Toast.makeText(context, "Login efetuado com sucesso.", Toast.LENGTH_SHORT).show();

                        if(callback != null){
                            callback.run();
                        }

                        Intent intent = new Intent(context, dadosCadastrais.class);
                        context.startActivity(intent);

                        if(context instanceof AppCompatActivity){
                            ((AppCompatActivity) context).finish();
                        }
                    } else {
                        Toast.makeText(context, "Erro ao obter token.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Credenciais inválidas.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(context, "Erro de rede: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}