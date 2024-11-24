package com.example.telas.api;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.telas.ActivityFormasLogin;
import com.example.telas.ActivityTelaPrincipal;
import com.example.telas.DadosCadastrais;
import com.example.telas.SharedPreferencesManager;
import com.example.telas.model.GoogleLoginRequest;
import com.example.telas.model.JwtResponse;
import com.example.telas.model.LoginRequest;
import com.example.telas.model.LoginResponse;
import com.example.telas.model.MessageResponse;
import com.example.telas.model.ProfileResponse;
import com.example.telas.model.RegisterRequest;
import com.example.telas.model.dto.UserDTO;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

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
     * Realiza o login do usuário via Google.
     *
     * @param idToken O token de identificação do Google.
     */
    public void realizarLoginGoogle(String idToken) {
        AuthService authService = ApiClient.getClient().create(AuthService.class);

        GoogleLoginRequest googleLoginRequest = new GoogleLoginRequest(idToken);

        Call<JwtResponse> call = authService.authenticateGoogleUser(googleLoginRequest);
        call.enqueue(new Callback<JwtResponse>() {
            @Override
            public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                if(response.isSuccessful()){
                    JwtResponse jwtResponse = response.body();
                    if(jwtResponse != null && jwtResponse.getToken() != null){

                        sharedPreferencesManager.saveAuthToken(jwtResponse.getToken());

                        // Salvar o nome do usuário
                        if(jwtResponse.getUsername() != null){
                            sharedPreferencesManager.saveUsername(jwtResponse.getUsername());
                        } else {
                            // Se o nome do usuário não estiver na resposta, você pode implementar uma chamada para obter os detalhes do usuário
                            fetchUserInfo();
                        }

                        Toast.makeText(context, "Login com Google efetuado com sucesso.", Toast.LENGTH_SHORT).show();

                        // Verificar se o perfil existe
                        verificarPerfil();
                    } else {
                        Toast.makeText(context, "Erro ao obter token.", Toast.LENGTH_SHORT).show();
                        Log.e("AuthManager", "Erro ao obter token: jwtResponse é nulo ou token é nulo.");
                    }
                } else {
                    Toast.makeText(context, "Token do Google inválido.", Toast.LENGTH_SHORT).show();
                    Log.e("AuthManager", "Resposta não bem-sucedida: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JwtResponse> call, Throwable t) {
                Toast.makeText(context, "Erro de rede: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("AuthManager", "Falha na requisição: " + t.getMessage());
            }
        });
    }

    /**
     * Realiza o login do usuário com email e senha.
     *
     * @param email O email do usuário.
     * @param senha A senha do usuário.
     */
    public void realizarLogin(String email, String senha) {
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

                        // Salvar o nome do usuário
                        if(loginResponse.getUsername() != null){
                            sharedPreferencesManager.saveUsername(loginResponse.getUsername());
                        } else {
                            // Se o nome do usuário não estiver na resposta, você pode implementar uma chamada para obter os detalhes do usuário
                            fetchUserInfo();
                        }

                        Toast.makeText(context, "Login efetuado com sucesso.", Toast.LENGTH_SHORT).show();

                        // Verificar se o perfil existe
                        verificarPerfil();
                    } else {
                        Toast.makeText(context, "Erro ao obter token.", Toast.LENGTH_SHORT).show();
                        Log.e("AuthManager", "Erro ao obter token: loginResponse é nulo ou token é nulo.");
                    }
                } else {
                    Toast.makeText(context, "Credenciais inválidas.", Toast.LENGTH_SHORT).show();
                    Log.e("AuthManager", "Resposta não bem-sucedida: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(context, "Erro de rede: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("AuthManager", "Falha na requisição: " + t.getMessage());
            }
        });
    }

    /**
     * Realiza o registro de um novo usuário com username, email e senha.
     *
     * @param username O nome de usuário.
     * @param email O email do usuário.
     * @param senha A senha do usuário.
     */
    public void realizarRegistro(String username, String email, String senha) {
        AuthService authService = ApiClient.getClient().create(AuthService.class);

        RegisterRequest registerRequest = new RegisterRequest(username, email, senha);

        Call<MessageResponse> call = authService.registerUser(registerRequest);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if(response.isSuccessful()){
                    MessageResponse messageResponse = response.body();
                    if(messageResponse != null){
                        Toast.makeText(context, messageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Registro efetuado com sucesso.", Toast.LENGTH_SHORT).show();
                    }
                    realizarLogin(email, senha);
                } else {
                    // Tentar obter a mensagem de erro da resposta
                    try {
                        String errorBody = response.errorBody().string();
                        // Usando Gson para parsear o erro
                        Gson gson = new Gson();
                        MessageResponse errorResponse = gson.fromJson(errorBody, MessageResponse.class);
                        if(errorResponse != null && errorResponse.getMessage() != null){
                            Toast.makeText(context, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Erro no registro. Código: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Erro no registro. Código: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                    Log.e("AuthManager", "Resposta não bem-sucedida: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(context, "Erro de rede: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("AuthManager", "Falha na requisição: " + t.getMessage());
            }
        });
    }


    /**
     * Verifica se o perfil do usuário existe no backend.
     */
    public void verificarPerfil() {
        String token = getAuthToken();
        if(token == null){
            Toast.makeText(context, "Token de autenticação não encontrado. Por favor, faça login novamente.", Toast.LENGTH_SHORT).show();
            // Redirecionar para a tela de login
            Intent intent = new Intent(context, ActivityFormasLogin.class);
            context.startActivity(intent);
            if(context instanceof AppCompatActivity){
                ((AppCompatActivity) context).finish();
            }
            return;
        }

        ProfileService profileService = ApiClient.getClient().create(ProfileService.class);
        Call<List<ProfileResponse>> call = profileService.getProfiles("Bearer " + token);
        call.enqueue(new Callback<List<ProfileResponse>>() {
            @Override
            public void onResponse(Call<List<ProfileResponse>> call, Response<List<ProfileResponse>> response) {
                if(response.isSuccessful()){
                    List<ProfileResponse> profiles = response.body();
                    Log.d("AuthManager", "Resposta da API de perfil: " + profiles);

                    if(profiles != null && !profiles.isEmpty()){
                        if(profiles.size() == 1){
                            // Se apenas um perfil, redirecionar para a tela principal
                            Log.d("AuthManager", "Um único perfil encontrado. Redirecionando para ActivityTelaPrincipal.");
                            Intent intent = new Intent(context, ActivityTelaPrincipal.class);
                            context.startActivity(intent);
                            if(context instanceof AppCompatActivity){
                                ((AppCompatActivity) context).finish();
                            }
                        } else {
                            // Se houver múltiplos perfis, selecionar o primeiro ou implementar lógica para seleção
                            Log.d("AuthManager", "Múltiplos perfis encontrados. Redirecionando para ActivityTelaPrincipal.");
                            // Você pode implementar uma lógica para selecionar qual perfil usar
                            Intent intent = new Intent(context, ActivityTelaPrincipal.class);
                            context.startActivity(intent);
                            if(context instanceof AppCompatActivity){
                                ((AppCompatActivity) context).finish();
                            }
                        }
                    } else {
                        // Perfil não existe, redirecionar para a tela de registro de perfil
                        Log.d("AuthManager", "Nenhum perfil encontrado. Redirecionando para DadosCadastrais.");
                        Intent intent = new Intent(context, DadosCadastrais.class);
                        context.startActivity(intent);
                        if(context instanceof AppCompatActivity){
                            ((AppCompatActivity) context).finish();
                        }
                    }
                } else {
                    Log.e("AuthManager", "Erro na resposta da API de perfil: " + response.code());

                    if(response.code() == 404){
                        // Perfil não existe, redirecionar para a tela de registro de perfil
                        Log.d("AuthManager", "Perfil não encontrado (404). Redirecionando para DadosCadastrais.");
                        Intent intent = new Intent(context, DadosCadastrais.class);
                        context.startActivity(intent);
                        if(context instanceof AppCompatActivity){
                            ((AppCompatActivity) context).finish();
                        }
                    } else {
                        Toast.makeText(context, "Erro ao verificar perfil. Código: " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.e("AuthManager", "Erro ao verificar perfil: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ProfileResponse>> call, Throwable t) {
                Toast.makeText(context, "Erro de rede ao verificar perfil: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("AuthManager", "Falha ao verificar perfil: " + t.getMessage());
            }
        });
    }


    private void fetchUserInfo() {
        UserService userService = ApiClient.getClient().create(UserService.class);
        Call<UserDTO> call = userService.getCurrentUser("Bearer " + getAuthToken());
        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if(response.isSuccessful()){
                    UserDTO user = response.body();
                    if(user != null && user.getUsername() != null){
                        sharedPreferencesManager.saveUsername(user.getUsername());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Log.e("AuthManager", "Falha ao buscar informações do usuário: " + t.getMessage());
            }
        });
    }


    /**
     * Verifica se o usuário está logado.
     *
     * @return true se o usuário possui um token JWT armazenado, false caso contrário.
     */
    public boolean isUserLoggedIn() {
        return sharedPreferencesManager.getAuthToken() != null;
    }

    /**
     * Obtém o token JWT armazenado.
     *
     * @return O token JWT ou null se não estiver armazenado.
     */
    public String getAuthToken() {
        return sharedPreferencesManager.getAuthToken();
    }
}