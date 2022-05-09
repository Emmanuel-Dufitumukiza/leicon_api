package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customers {
private String username;
private final String telephone;
private final String secretPin;
private final String email;
private final String district;
private final String sector;

private final String gateId;

private final int id;

public final String date;

    public Customers(
            @JsonProperty("username") String username,
            @JsonProperty("telephone") String telephone,
            @JsonProperty("secretPin") String secretPin,
            @JsonProperty("email") String email,
            @JsonProperty("district") String district,
            @JsonProperty("sector") String sector,
            @JsonProperty("gateIds") String gateId,
            @JsonProperty("date") String date,
            @JsonProperty("id") int id
            ) {

            this.username = username;
            this.telephone = telephone;
            this.secretPin = secretPin;
            this.email = email;
            this.district = district;
            this.sector = sector;
            this.gateId = gateId;
            this.date = date;
            this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
       this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getSecretPin() {
        return secretPin;
    }

    public String getEmail() {
        return email;
    }

    public String getDistrict() {
        return district;
    }

    public String getSector() {
        return sector;
    }

    public String getGateId() {
        return gateId;
    }

    public String getDate() {
        return date;
    }
}
