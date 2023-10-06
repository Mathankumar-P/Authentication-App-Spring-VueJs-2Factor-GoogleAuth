package com.springauthendication.auth.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.net.http.HttpResponse;

public class EmailAlreadyExistsError extends ResponseStatusException {

    public EmailAlreadyExistsError() {
        super(HttpStatus.BAD_REQUEST, "Email Already Exists");
    }
}
