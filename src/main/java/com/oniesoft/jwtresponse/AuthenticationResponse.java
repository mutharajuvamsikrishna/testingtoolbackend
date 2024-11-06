package com.oniesoft.jwtresponse;

public class AuthenticationResponse {
    private String jwt;
    private String role;

    // Constructors, getters, and setters
    public AuthenticationResponse(String jwt, String role) {
        this.jwt = jwt;
        this.role = role;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "jwt='" + jwt + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}