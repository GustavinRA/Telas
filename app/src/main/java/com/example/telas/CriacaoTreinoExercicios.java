package com.example.telas;

import java.util.List;

public class CriacaoTreinoExercicios {

    private String name;            // Nome do exercício
    private List<String> muscles;   // Grupos musculares que o exercício trabalha
    private boolean isAdded;        // Indica se o exercício foi adicionado ou não
    private String series;          // Número de séries
    private String repetitions;     // Número de repetições
    private String rest;            // Tempo de descanso

    public CriacaoTreinoExercicios(String name, List<String> muscles, boolean isAdded) {
        this.name = name;
        this.muscles = muscles;
        this.isAdded = isAdded;
        this.series = "";
        this.repetitions = "";
        this.rest = "";
    }

    // Métodos de acesso
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Método para obter os músculos como uma string
    public String getMusclesAsString() {
        if (muscles == null || muscles.isEmpty()) {
            return "Nenhum grupo muscular selecionado";
        }
        return String.join(", ", muscles); // Converte a lista de músculos para uma string separada por vírgulas
    }

    public List<String> getMuscles() {
        return muscles;
    }

    public void setMuscles(List<String> muscles) {
        this.muscles = muscles;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean isAdded) {
        this.isAdded = isAdded;
    }

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
}
