package com.example.telas.model;

import java.util.Set;

public class Exercise {
    private Long exerciseId;
    private String nome;
    private Set<String> musculosAfetados;

    public Set<String> getMusculosAfetados() {
        return musculosAfetados;
    }

    public void setMusculosAfetados(Set<String> musculosAfetados) {
        this.musculosAfetados = musculosAfetados;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }
}