package org.example.model;
import lombok.*;
import org.example.enums.UserRole;
import org.example.enums.UserStatus;

import javax.management.relation.Role;
import java.util.UUID;

@Setter
@Getter
public class User {
    private String name;
    private String email;
    private String password;
    private UUID userID;
    private UserRole role;
    private UserStatus status;

    public User() {}
    public User(String name, String email, String password, UUID userID) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userID = userID;
        this.role = UserRole.USER;
        this.status = UserStatus.ACTIVE;

    }
    @Override
    public String toString() {
        return "Имя: " + name + "email: " + email + "userID: " + userID;
    }
}
