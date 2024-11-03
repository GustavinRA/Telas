package com.example.telas;

import java.util.HashSet;
import java.util.Set;

public class Exercicio {
    private String nome;
    private Set<String> musculosAfetados;

    public Exercicio(String nome) {
        this.nome = nome;
        this.musculosAfetados = new HashSet<>();
    }

    public String getNome() {
        return nome;
    }

    public Set<String> getMusculosAfetados() {
        return musculosAfetados;
    }

    public String getMusculosComoTexto() {
        return String.join(", ", musculosAfetados);
    }

    public void addMusculo(String musculo) {
        musculosAfetados.add(musculo);
    }

    public void removeMusculo(String musculo) {
        musculosAfetados.remove(musculo);
    }
}
