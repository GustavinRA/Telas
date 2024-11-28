package com.example.telas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.telas.api.ApiClient;
import com.example.telas.api.AuthManager;
import com.example.telas.api.ProfileService;
import com.example.telas.model.ProfileRequest;
import com.example.telas.model.ProfileResponse;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DadosCadastrais extends AppCompatActivity {
    private Button buttonDC;
    private EditText editTextNome, editTextPeso, editTextAltura, editTextDate;
    private Spinner spinnerGenero;
    private AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dados_cadastrais);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        authManager = new AuthManager(this); // Inicializar AuthManager

        // Inicializar Views
        buttonDC = findViewById(R.id.buttonDCB);
        editTextNome = findViewById(R.id.editTextText);
        editTextPeso = findViewById(R.id.editTextText2);
        editTextAltura = findViewById(R.id.editTextText3);
        spinnerGenero = findViewById(R.id.spinnerGenero); // Mover para aqui
        editTextDate = findViewById(R.id.editTextDate);

        ArrayAdapter<CharSequence> adapterGenero = ArrayAdapter.createFromResource(
                this,
                R.array.genero_array,
                android.R.layout.simple_spinner_item
        );
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenero.setAdapter(adapterGenero);

        // Configurar o botão de envio de perfil
        buttonDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarPerfil();
            }
        });

        // Opcional: Configurar o botão de voltar
        findViewById(R.id.botaoVoltar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Finaliza a atividade atual para voltar à anterior
            }
        });
    }

    private void enviarPerfil(){
        String nome = editTextNome.getText().toString().trim();
        String pesoStr = editTextPeso.getText().toString().trim();
        String alturaStr = editTextAltura.getText().toString().trim();
        String genero = spinnerGenero.getSelectedItem().toString();
        String dataNascimentoStr = editTextDate.getText().toString().trim();

        // Validação dos campos
        if(nome.isEmpty() || pesoStr.isEmpty() || alturaStr.isEmpty() || genero.isEmpty() || dataNascimentoStr.isEmpty()){
            Toast.makeText(DadosCadastrais.this, "Por favor, preencha todos os campos de perfil.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Log do gênero selecionado
        Log.d("DadosCadastrais", "Valor de genero selecionado: " + genero);

        float peso, altura;
        byte idade;

        // Converter peso e altura
        try{
            peso = Float.parseFloat(pesoStr);
            altura = Float.parseFloat(alturaStr);
        } catch (NumberFormatException e){
            Toast.makeText(DadosCadastrais.this, "Por favor, insira valores válidos para peso e altura.", Toast.LENGTH_SHORT).show();
            Log.e("dadosCadastrais", "Erro de conversão: " + e.getMessage());
            return;
        }

        // Calcular idade a partir da data de nascimento
        try{
            idade = calcularIdade(dataNascimentoStr);
            if(idade < 0){
                Toast.makeText(DadosCadastrais.this, "Data de nascimento inválida.", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (ParseException e){
            Toast.makeText(DadosCadastrais.this, "Formato de data inválido. Use DD/MM/AAAA.", Toast.LENGTH_SHORT).show();
            Log.e("dadosCadastrais", "Erro de parse: " + e.getMessage());
            return;
        }

        // Criar o objeto de requisição de perfil
        ProfileRequest profileRequest = new ProfileRequest(idade, (short) altura, peso, genero);
        Log.d("DadosCadastrais", "ProfileRequest criado: " + profileRequest.toString());

        // Serializar para JSON e verificar
        Gson gson = new Gson();
        String json = gson.toJson(profileRequest);
        Log.d("DadosCadastrais", "JSON serializado do ProfileRequest: " + json);

        // Obter o token JWT do SharedPreferences
        String token = authManager.getAuthToken();

        if(token == null){
            Toast.makeText(DadosCadastrais.this, "Token de autenticação não encontrado. Por favor, faça login novamente.", Toast.LENGTH_SHORT).show();
            Log.e("dadosCadastrais", "Token JWT é nulo.");
            // Redirecionar para a tela de login
            Intent intent = new Intent(DadosCadastrais.this, ActivityFormasLogin.class);
            startActivity(intent);
            finish();
            return;
        }

        // Criar o serviço de perfil
        ProfileService profileService = ApiClient.getClient().create(ProfileService.class);

        // Fazer a chamada à API para registrar o perfil
        Call<ProfileResponse> call = profileService.createProfile("Bearer " + token, profileRequest);
        Log.d("dadosCadastrais", "Enviando perfil: " + json);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if(response.isSuccessful()){
                    ProfileResponse profileResponse = response.body();
                    if(profileResponse != null){
                        Toast.makeText(DadosCadastrais.this, "Perfil registrado com sucesso!", Toast.LENGTH_SHORT).show();
                        Log.d("dadosCadastrais", "Perfil registrado: " + profileResponse.toString());
                        // Redirecionar para a tela principal
                        Intent intent = new Intent(DadosCadastrais.this, ActivityTelaPrincipal.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(DadosCadastrais.this, "Erro ao registrar perfil: resposta nula.", Toast.LENGTH_SHORT).show();
                        Log.e("dadosCadastrais", "ProfileResponse é nulo.");
                    }
                } else {
                    Toast.makeText(DadosCadastrais.this, "Erro ao registrar perfil. Código: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("dadosCadastrais", "Resposta não bem-sucedida: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(DadosCadastrais.this, "Erro de rede: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("dadosCadastrais", "Falha na requisição: " + t.getMessage());
            }
        });
    }

    /**
     * Calcula a idade com base na data de nascimento no formato DD/MM/AAAA.
     *
     * @param dataNascimentoStr Data de nascimento como string.
     * @return Idade em anos.
     * @throws ParseException Se a data estiver em formato inválido.
     */
    private byte calcularIdade(String dataNascimentoStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date dataNascimento = sdf.parse(dataNascimentoStr);
        if(dataNascimento == null){
            throw new ParseException("Data inválida", 0);
        }

        // Calcular a idade
        Calendar hoje = Calendar.getInstance();
        Calendar nascimento = Calendar.getInstance();
        nascimento.setTime(dataNascimento);

        int idade = hoje.get(Calendar.YEAR) - nascimento.get(Calendar.YEAR);

        if (hoje.get(Calendar.DAY_OF_YEAR) < nascimento.get(Calendar.DAY_OF_YEAR)){
            idade--;
        }

        return (byte) idade;
    }
}