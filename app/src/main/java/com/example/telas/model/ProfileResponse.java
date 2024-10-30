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

    // Getters e Setters
    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

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