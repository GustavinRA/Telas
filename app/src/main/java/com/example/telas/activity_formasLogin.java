package com.example.telas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.telas.dao.DAO;
import com.example.telas.model.Usuario;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class activity_formasLogin extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    Button google_btn;
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

        google_btn = findViewById(R.id.entrarGoogle);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();
            }
        });
        Button outraOpcao = findViewById(R.id.outraOpcao);

        outraOpcao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_formasLogin.this, activity_cadastro.class);
                startActivity(intent);
            }
        });


        Button login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_formasLogin.this, activity_login.class);
                startActivity(intent);
            }
        });


        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            // O usuário já está logado, vá para a próxima atividade
            nextActivity();
        }

    }
    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    // Obter informações do usuário
                    String email = account.getEmail();

                    // Criar um objeto Usuario
                    Usuario usuario = new Usuario();
                    usuario.setEmail(email);

                    // Definir uma senha padrão para o cadastro via Google
                    String senhaPadrao = "google_login"; // Pode ser qualquer valor fixo ou algo gerado dinamicamente
                    usuario.setSenha(senhaPadrao);

                    // Inserir o usuário no banco de dados
                    DAO dao = new DAO(this);
                    String resultado = dao.insereUsuario(usuario);

                    if (resultado.equals("Sucesso ao cadastrar o usuário") || resultado.equals("Usuário já existente")) {
                        // Usuário cadastrado com sucesso ou já existente
                        nextActivity();
                    } else {
                        Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (ApiException e) {
                Toast.makeText(this, "Erro de API: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (Exception e) {
                Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    void nextActivity(){
        finish();
        Intent intent = new Intent(activity_formasLogin.this, activity_login.class);
        startActivity(intent);
    }
}