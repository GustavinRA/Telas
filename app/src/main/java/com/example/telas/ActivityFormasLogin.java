package com.example.telas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.telas.api.AuthManager;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;

public class ActivityFormasLogin extends AppCompatActivity {

    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private ActivityResultLauncher<IntentSenderRequest> signInLauncher;
    private AuthManager authManager;
    private Button google_btn;
    private Button outraOpcao;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formas_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        authManager = new AuthManager(this); // Inicializar AuthManager

        google_btn = findViewById(R.id.entrarGoogle);
        outraOpcao = findViewById(R.id.outraOpcao);
        loginButton = findViewById(R.id.login); // Associar o botão "Login"

        // Configurar o SignInClient
        oneTapClient = Identity.getSignInClient(this);

        // Configurar o BeginSignInRequest
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                .setSupported(true)
                                .setServerClientId(getString(R.string.default_web_client_id))
                                .setFilterByAuthorizedAccounts(false)
                                .build())
                .setAutoSelectEnabled(true)
                .build();

        // Registrar o ActivityResultLauncher para One Tap Sign-In
        signInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartIntentSenderForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        try {
                            // Obter o SignInCredential da intenção
                            SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(result.getData());
                            String idToken = credential.getGoogleIdToken();
                            String email = credential.getId(); // O email do usuário

                            if (idToken != null) {
                                // Enviar o idToken para o backend via AuthManager
                                authManager.realizarLoginGoogle(idToken);
                            } else {
                                Toast.makeText(this, "Falha ao obter o token do Google.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(this, "Falha no login com Google.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Configurar o botão de login com Google
        google_btn.setOnClickListener(v -> signIn());

        // Configurar o botão de outra opção (registro)
        outraOpcao.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityFormasLogin.this, ActivityCadastro.class);
            startActivity(intent);
        });

        // Configurar o botão "Login" para redirecionar para ActivityLogin
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityFormasLogin.this, ActivityLogin.class);
            startActivity(intent);
        });

        // Verificar se o usuário já está logado
        if (authManager.isUserLoggedIn()) {
            authManager.verificarPerfil();
        }
    }

    private void signIn() {
        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this, result -> {
                    try {
                        IntentSenderRequest request = new IntentSenderRequest.Builder(result.getPendingIntent().getIntentSender()).build();
                        signInLauncher.launch(request);
                    } catch (Exception e) {
                        Toast.makeText(this, "Erro ao iniciar o login: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                })
                .addOnFailureListener(this, e -> {
                    Toast.makeText(this, "Falha ao iniciar o login: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}