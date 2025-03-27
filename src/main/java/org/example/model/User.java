package org.example.model;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
public class User {
    private String name;
    private String email;
    private String password;
    private UUID userID;

    public User() {}
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userID = UUID.randomUUID();
    }
}
