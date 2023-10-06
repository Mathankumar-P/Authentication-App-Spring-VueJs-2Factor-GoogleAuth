package com.springauthendication.auth.service;

import com.springauthendication.auth.error.EmailAlreadyExistsError;
import com.springauthendication.auth.error.InvalidCredentialsError;
import com.springauthendication.auth.error.PasswordNotMatchingError;
import com.springauthendication.auth.model.User;
import com.springauthendication.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.core.userdetails.UserDetailsResourceFactoryBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

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

    public User login(String email, String password) {
        //find user by mail Id
        var user = userRepository.findByEmail(email).orElseThrow(InvalidCredentialsError::new);
        //check if the password matches
        if(! passwordEncoder.matches(password, user.getPassword()))
            throw new PasswordNotMatchingError();
        return user;
    }
}
