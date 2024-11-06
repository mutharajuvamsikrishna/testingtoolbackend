package com.oniesoft.jwtresponse;

import com.oniesoft.model.Register;

public class AuthenticationResponse {
    private String jwt;
    private Register register;

    // Constructors, getters, and setters
    public AuthenticationResponse(String jwt, Register register) {
        this.jwt = jwt;
        this.register = register;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "jwt='" + jwt + '\'' +
                ", register=" + register +
                '}';
    }
}