package com.example.telas.recyclerviewTreinos;

public class Exercicio {
    private String nome;
    private int series;
    private int repeticoes;
    private String descanso;
    private String musculosAfetados;

    // Construtor
    public Exercicio(String nome, int series, int repeticoes, String descanso) {
        this.nome = nome;
        this.series = series;
        this.repeticoes = repeticoes;
        this.descanso = descanso;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }

    public String getDescanso() {
        return descanso;
    }

    public void setDescanso(String descanso) {
        this.descanso = descanso;
    }

    public String getMusculosAfetados() {
        return musculosAfetados;  // Getter do novo campo
    }

    public void setMusculosAfetados(String musculosAfetados) {
        this.musculosAfetados = musculosAfetados; // Setter do novo campo
    }
}
