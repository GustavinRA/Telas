package com.example.telas.recyclerviewPesquisa;

public class UsuarioPerfil {
    private String nome;
    private int imagemResId;

    public UsuarioPerfil(String nome, int imagemResId) {
        this.nome = nome;
        this.imagemResId = imagemResId;
    }

    public String getNome() {
        return nome;
    }

    public int getImagemResId() {
        return imagemResId;
    }
}
