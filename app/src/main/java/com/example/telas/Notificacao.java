package com.example.telas;

import java.util.Date;

public class Notificacao {
    public static final int TIPO_TREINO = 0;
    public static final int TIPO_MENSAGEM = 1;
    public static final int TIPO_SUGESTAO_TREINO = 2;

    private int tipo;
    private String titulo;
    private String conteudo;
    private Date dataTreino;

    public Notificacao(int tipo, String titulo, String conteudo, Date dataTreino) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.dataTreino = dataTreino;
    }

    public int getTipo() {
        return tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public Date getDataTreino() {
        return dataTreino;
    }
}