package com.joo.ball.dto.auth.request;

public record SignUpRequest(String username, String password) {

    public static SignUpRequest of(String username, String password){
        return new SignUpRequest(username, password);
    }
}
