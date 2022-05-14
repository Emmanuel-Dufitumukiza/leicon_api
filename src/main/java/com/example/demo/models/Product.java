package com.example.demo.models;

public class Product {
    private final String remoteCode;
    private final String password;

    private final int gateId;

    private final String status;

    public final String date;

    public Product(String remoteCode, String password, int gateId, String status, String date) {
        this.remoteCode = remoteCode;
        this.password = password;
        this.gateId = gateId;
        this.status = status;
        this.date = date;
    }

    public String getRemoteCode() {
        return remoteCode;
    }

    public String getPassword() {
        return password;
    }

    public int getGateId() {
        return gateId;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }
}