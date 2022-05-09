package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Admins {
    private final String email;
    private final String username;
    private final String password;

    public Admins(@JsonProperty("email") String email,@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
