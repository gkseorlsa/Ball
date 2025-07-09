package com.joo.ball.factory;

public class UserFactory {

    public static User of(SignUpRequest signUpRequest, String encryptedPassword) {
        return User.builder()
            .username(signUpRequest.username())
            .password(encryptedPassword)
            .build();
    }
}
