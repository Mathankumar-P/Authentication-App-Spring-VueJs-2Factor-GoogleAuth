package com.springauthendication.auth.service;

import com.springauthendication.auth.error.EmailAlreadyExistsError;
import com.springauthendication.auth.error.InvalidCredentialsError;
import com.springauthendication.auth.error.PasswordNotMatchingError;
import com.springauthendication.auth.model.User;
import com.springauthendication.auth.repository.UserRepository;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Value;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Objects;

@Service
public class AuthService {
    @Getter
    private final UserRepository userRepository;
    @Getter
    private final PasswordEncoder passwordEncoder;
    private final String accessToken;
    private final String refreshToken;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       String accessToken, String refreshToken) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public User registerService(String first_name , String last_name, String email, String password, String password_confirm) {
        if(!Objects.equals(password, password_confirm))
            throw new InvalidCredentialsError();
        User user;
        try{
             user =  userRepository.save( User.of(first_name ,  last_name,  email,  passwordEncoder.encode(password)));
        }catch(DbActionExecutionException e){
            throw new EmailAlreadyExistsError();
        }
        return user;
    }

    public Token login(String email, String password) {
        //find user by mail ID
        var user = userRepository.findByEmail(email).orElseThrow(InvalidCredentialsError::new);
        //check if the password matches
        if(! passwordEncoder.matches(password, user.getPassword()))
            throw new PasswordNotMatchingError();
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        System.out.println(secretKey.toString());
        return Token.of(user.getId(), 10L, String.valueOf(secretKey));
    }
}
