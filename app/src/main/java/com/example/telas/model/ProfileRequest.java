package com.example.telas.model;

import com.google.gson.annotations.SerializedName;

public class ProfileRequest {
    @SerializedName("age")
    private byte age;

    @SerializedName("height")
    private short height; // Em centímetros

    @SerializedName("weight")
    private float weight; // Em quilogramas

    // Construtor
    public ProfileRequest(byte age, short height, float weight) {
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    // Getters e Setters
    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public short getHeight() {
        return height;
    }

    public void setHeight(short height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}