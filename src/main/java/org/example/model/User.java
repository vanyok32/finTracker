package org.example.model;
import lombok.*;

@Setter
@Getter
public class User {
    private String name;
    private String email;
    private String password;
    private int userID;

    User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userID = (int) Math.random();
    }
}
