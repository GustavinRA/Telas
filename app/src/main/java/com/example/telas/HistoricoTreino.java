package com.example.telas;

public class HistoricoTreino {

    private String nome;
    private String descricao;
    private String anotacao;
    private int ano;
    private int mes;
    private int dia;

    public HistoricoTreino(String nome, String descricao, String anotacao, int ano, int mes, int dia) {
        this.nome = nome;
        this.descricao = descricao;
        this.anotacao = anotacao;
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getAnotacao() {
        return anotacao;
    }

    public int getAno() {
        return ano;
    }

    public int getMes() {
        return mes;
    }

    public int getDia() {
        return dia;
    }
}
