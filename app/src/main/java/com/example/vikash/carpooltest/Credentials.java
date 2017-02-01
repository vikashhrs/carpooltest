package com.example.vikash.carpooltest;

import java.io.Serializable;

/**
 * Created by vikash on 31-Dec-16.
 */
public class Credentials implements Serializable {
    private String token;
    private String name;

    public Credentials(String token, String name) {
        this.token = token;
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
