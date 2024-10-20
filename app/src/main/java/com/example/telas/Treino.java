package com.example.telas;

import java.util.Date;

public class Treino {
    private String titulo;
    private String descricao;
    private Date data;

        public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }


    public void setData(Date data) {
        this.data = data;
    }
}
