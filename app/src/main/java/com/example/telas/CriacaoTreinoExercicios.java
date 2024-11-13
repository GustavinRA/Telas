package com.example.telas;

/**
 * Classe modelo para representar exercícios na criação de treinos.
 */
public class CriacaoTreinoExercicios {
    private Long exerciseId;
    private String name;
    private String muscles;
    private boolean isAdded;
    private String series;
    private String repetitions;
    private String rest;

    public CriacaoTreinoExercicios(Long exerciseId, String name, String muscles, boolean isAdded) {
        this.exerciseId = exerciseId;
        this.name = name;
        this.muscles = muscles;
        this.isAdded = isAdded;
        this.series = "";
        this.repetitions = "";
        this.rest = "";
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
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