package com.example.telas.recyclerviewPesquisa;

import android.os.Bundle;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityPesquisa extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PerfilAdapter perfilAdapter;
    private List<UsuarioPerfil> listaPerfis;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa); // Certifique-se de que o nome do arquivo XML está correto

        // Inicializa o SearchView e o RecyclerView
        searchView = findViewById(R.id.SearchView);
        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa a lista de perfis
        listaPerfis = new ArrayList<>();
        listaPerfis.add(new UsuarioPerfil("Usuário 1", R.drawable.img_4));
        listaPerfis.add(new UsuarioPerfil("Usuário 2", R.drawable.img_4));
        listaPerfis.add(new UsuarioPerfil("Usuário 3", R.drawable.img_4));
        listaPerfis.add(new UsuarioPerfil("Usuário 4", R.drawable.img_4));
        listaPerfis.add(new UsuarioPerfil("Usuário 5", R.drawable.img_4));

        // Configura o Adapter e o RecyclerView
        perfilAdapter = new PerfilAdapter(listaPerfis, this);
        recyclerView.setAdapter(perfilAdapter);

        // Implementa a lógica de pesquisa
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<UsuarioPerfil> listaFiltrada = new ArrayList<>();
                for (UsuarioPerfil perfil : listaPerfis) {
                    if (perfil.getNome().toLowerCase().contains(newText.toLowerCase())) {
                        listaFiltrada.add(perfil);
                    }
                }
                perfilAdapter = new PerfilAdapter(listaFiltrada, ActivityPesquisa.this);
                recyclerView.setAdapter(perfilAdapter);
                return true;
            }
        });
    }
}
