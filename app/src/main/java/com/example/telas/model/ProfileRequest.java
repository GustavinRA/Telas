package com.example.telas.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class ProfileRequest {
    @SerializedName("age")
    private byte age;

    @SerializedName("height")
    private short height; // Em centímetros

    @SerializedName("weight")
    private float weight; // Em quilogramas

    @SerializedName("gender")
    private String gender; // Novo campo adicionado

    // Construtor atualizado
    public ProfileRequest(byte age, short height, float weight, String gender) {
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
    }

    // Getters e Setters
    public byte getAge() { return age; }
    public void setAge(byte age) { this.age = age; }

    public short getHeight() { return height; }
    public void setHeight(short height) { this.height = height; }

    public float getWeight() { return weight; }
    public void setWeight(float weight) { this.weight = weight; }

    public String getGender() { return gender; } // Novo getter
    public void setGender(String gender) { this.gender = gender; } // Novo setter

    @Override
    public String toString() {
        return "ProfileRequest{" +
                "age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", gender='" + gender + '\'' +
                '}';
    }

}