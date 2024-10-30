package com.example.telas.model.dto;

public class UserDTO {

    UserDTO(Long userId, String username, String email){
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    private Long userId;
    private String username;
    private String email;

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}