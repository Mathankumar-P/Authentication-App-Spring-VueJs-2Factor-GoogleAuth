package com.springauthendication.auth.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginTokenGen {
    private final Token accessToken;
    private final Token refreshToken;

    public static LoginTokenGen of( int id, String accessSecret, String refreshSecret){
        return new LoginTokenGen(Token.of(id,1L,accessSecret), Token.of(id,1440L,refreshSecret));
    }
}
