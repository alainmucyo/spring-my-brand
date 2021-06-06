package com.example.my_brand.user;

import java.util.Date;

public class LoginResponse {
    private final String message;
    private final String token;
    private final Date expiryDate;

    public LoginResponse(String message, String token, Date expiryDate) {
        this.message = message;
        this.token = token;
        this.expiryDate = expiryDate;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
}
