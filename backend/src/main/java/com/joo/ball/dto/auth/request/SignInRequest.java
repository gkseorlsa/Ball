package com.joo.ball.dto.auth.request;

public record SignInRequest(String username, String password) {

    public static SignInRequest of(String username, String password){
        return new SignInRequest(username, password);
    }
}
