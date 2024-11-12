package com.example.telas;

import java.util.List;
import java.util.ArrayList;

public class Exercicio {

    private String nome;
    private List<String> musculos;  // Lista de músculos afetados
    private String series;
    private String repetitions;
    private String rest;
    private boolean isAdded;

    // Construtor
    public Exercicio(String nome, List<String> musculos) {
        this.nome = nome;
        this.musculos = musculos != null ? musculos : new ArrayList<>();  // Garante que musculos nunca seja nulo
    }

    // Getter para nome
    public String getNome() {
        return nome;
    }

    // Getter e Setter para músculos
    public List<String> getMusculos() {
        return musculos;
    }

    // Método que retorna uma string com os músculos afetados
    public String getMusculosAfetados() {
        if (musculos != null && !musculos.isEmpty()) {
            return String.join(", ", musculos);  // Retorna a lista de músculos como uma string separada por vírgulas
        }
        return "Nenhum músculo afetado";  // Caso não haja músculos
    }

    // Método para adicionar um músculo à lista de músculos
    public void addMusculo(String musculo) {
        if (musculo != null && !musculo.trim().isEmpty()) {
            musculos.add(musculo);  // Adiciona o músculo na lista
        }
    }

    // Método para remover um músculo da lista de músculos
    public void removeMusculo(String musculo) {
        if (musculo != null && musculos.contains(musculo)) {
            musculos.remove(musculo);  // Remove o músculo da lista
        }
    }

    // Métodos para séries, repetições e descanso
    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(String repetitions) {
        this.repetitions = repetitions;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    // Método para retornar a lista de músculos como texto (corrigido)
    public String getMusculosComoTexto() {
        if (musculos != null && !musculos.isEmpty()) {
            return String.join(", ", musculos);  // Retorna os músculos como uma string separada por vírgulas
        }
        return "Nenhum músculo afetado";  // Caso não haja músculos
    }
}
