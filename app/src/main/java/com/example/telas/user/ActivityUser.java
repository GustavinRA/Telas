package com.example.telas.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.telas.ActivityCriacaoExercicios;
import com.example.telas.ActivityCriacaoTreinos;
import com.example.telas.R;
import com.example.telas.SharedPreferencesManager;
import com.example.telas.api.ApiClient;
import com.example.telas.api.ProfileService;
import com.example.telas.model.ProfileResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityUser extends AppCompatActivity {

    private TextView nomeUser, pesoUser, alturaUser, idadeUser;
    private AppCompatButton buttonExercicio, buttonTreinos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Inicializa os TextViews
        nomeUser = findViewById(R.id.nomeUser);
        pesoUser = findViewById(R.id.pesoUser);
        alturaUser = findViewById(R.id.alturaUser);
        idadeUser = findViewById(R.id.idadeUser);

        buttonExercicio = findViewById(R.id.buttonExercicio);
        buttonTreinos = findViewById(R.id.buttonTreinos);


        buttonExercicio.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityUser.this, ActivityCriacaoExercicios.class);
            startActivity(intent);
        });

        buttonTreinos.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityUser.this, ActivityCriacaoTreinos.class);
            startActivity(intent);
        });

        // Nome do usuário
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(this);
        String username = sharedPreferencesManager.getUsername();
        nomeUser.setText(username);

        // Chamar a API para obter o perfil do usuário
        obterPerfil();

    }

    private void obterPerfil() {
        // Obter o token JWT do SharedPreferences
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(this);
        String token = sharedPreferencesManager.getAuthToken();

        // Criar o serviço de perfil
        ProfileService profileService = ApiClient.getClient().create(ProfileService.class);

        // Chamar o serviço para obter o perfil
        Call<List<ProfileResponse>> call = profileService.getProfiles("Bearer " + token);
        call.enqueue(new Callback<List<ProfileResponse>>() {
            @Override
            public void onResponse(Call<List<ProfileResponse>> call, Response<List<ProfileResponse>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    ProfileResponse profile = response.body().get(0); // Pegar o primeiro perfil da lista

                    // Atualizar os dados na interface com os rótulos
                    pesoUser.setText("Peso: " + profile.getWeight() + " kg");
                    alturaUser.setText("Altura: " + profile.getHeight() + " cm");
                    idadeUser.setText("Idade: " + profile.getAge() + " anos");

                }
            }

            @Override
            public void onFailure(Call<List<ProfileResponse>> call, Throwable t) {
                Toast.makeText(ActivityUser.this, "Erro de rede: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
