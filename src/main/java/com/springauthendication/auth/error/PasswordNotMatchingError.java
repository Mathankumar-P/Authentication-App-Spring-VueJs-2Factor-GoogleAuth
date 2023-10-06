package com.springauthendication.auth.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class PasswordNotMatchingError extends ResponseStatusException {

    public PasswordNotMatchingError(){
        super(HttpStatus.BAD_REQUEST,"Password Not Matching");
    }
}
