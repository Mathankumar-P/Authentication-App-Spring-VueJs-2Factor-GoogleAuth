package com.springauthendication.auth.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springauthendication.auth.model.User;
import com.springauthendication.auth.repository.UserRepository;
import com.springauthendication.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestController
@RequestMapping(value = "/api")
public class AuthController {
    @Autowired
    AuthService authService;

    @GetMapping(value="hello")
    public String hello(){
        return "hello";
    }

    record  RegisterRequest(@JsonProperty("first_name")String first_name,  @JsonProperty("last_name") String last_name,  String email, String password, @JsonProperty("password_confirm") String passwordConfirm) {}
    record RegisterResponse( int id , @JsonProperty("first_name")String first_name,  @JsonProperty("last_name") String last_name,  String email, String password ){}

    @PostMapping(value="/register")
    public RegisterResponse register (@RequestBody RegisterRequest registerRequest){
        var user =  authService.registerService (
                        registerRequest.first_name(),
                        registerRequest.last_name(),
                        registerRequest.email(),
                        registerRequest.password(),
                        registerRequest.passwordConfirm()
                );
        return  new RegisterResponse(user.getId(),user.getFirst_name(), user.getLast_name(),user.getEmail(), user.getPassword());
    }

    record LoginRequest (String email, String password){}
    record LoginResponse( String token ){}

    @PostMapping(value = "/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        var token = authService.login(loginRequest.email(), loginRequest.password());
        return  new LoginResponse (token.getToken());
    }


}
