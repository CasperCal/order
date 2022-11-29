package com.example.order.domain.security;

public class EmailPassWord {
    private final String username;
    private final String password;

    public EmailPassWord(String email, String password) {
        this.username = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
