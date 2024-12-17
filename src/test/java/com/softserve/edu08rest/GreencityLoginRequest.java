package com.softserve.edu08rest;

public class GreencityLoginRequest {
    private String email;
    private String password;
    private String secretKey;

    public GreencityLoginRequest(String email, String password, String secretKey) {
        this.email = email;
        this.password = password;
        this.secretKey = secretKey;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSecretKey() {
        return secretKey;
    }

    @Override
    public String toString() {
        return "GreencityLoginRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", secretKey='" + secretKey + '\'' +
                '}';
    }
}
