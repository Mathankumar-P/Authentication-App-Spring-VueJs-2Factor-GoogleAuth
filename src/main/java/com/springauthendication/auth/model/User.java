package com.springauthendication.auth.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private int  id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;

    public static User of ( String first_name, String last_name, String email, String password){
        return new User(  first_name,  last_name,  email,  password);
    }

    public User(String first_name, String last_name, String email, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }
}
