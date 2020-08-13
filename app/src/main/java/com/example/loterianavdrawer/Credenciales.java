package com.example.loterianavdrawer;

import java.io.Serializable;

public class Credenciales implements Serializable {
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
