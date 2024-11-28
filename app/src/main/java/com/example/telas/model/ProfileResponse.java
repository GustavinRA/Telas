package com.example.telas.model;

import com.google.gson.annotations.SerializedName;

public class ProfileResponse {
    @SerializedName("profileId")
    private Long profileId;

    @SerializedName("age")
    private byte age;

    @SerializedName("height")
    private short height;

    @SerializedName("weight")
    private float weight;

    @SerializedName("gender") // Novo campo adicionado
    private String gender;

    // Getters e Setters
    public Long getProfileId() { return profileId; }
    public void setProfileId(Long profileId) { this.profileId = profileId; }

    public byte getAge() { return age; }
    public void setAge(byte age) { this.age = age; }

    public short getHeight() { return height; }
    public void setHeight(short height) { this.height = height; }

    public float getWeight() { return weight; }
    public void setWeight(float weight) { this.weight = weight; }

    public String getGender() { return gender; } // Getter para gender
    public void setGender(String gender) { this.gender = gender; } // Setter para gender

    // Sobrescrever o método toString()
    @Override
    public String toString() {
        return "ProfileResponse{" +
                "profileId=" + profileId +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", gender='" + gender + '\'' +
                '}';
    }
}