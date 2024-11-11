package com.example.telas;

public class CriacaoTreinoExercicios {
    private String name;
    private String muscles;
    private boolean isAdded;  // Estado se o exercício foi adicionado
    private String series;
    private String repetitions;
    private String rest;

    public CriacaoTreinoExercicios(String name, String muscles, boolean isAdded) {
        this.name = name;
        this.muscles = muscles;
        this.isAdded = isAdded;
        this.series = "";
        this.repetitions = "";
        this.rest = "";
    }

    public String getName() {
        return name;
    }

    public String getMuscles() {
        return muscles;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
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
