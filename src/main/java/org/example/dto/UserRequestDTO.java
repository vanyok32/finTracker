package org.example.dto;
import lombok.*;
@Getter
@Setter
public class UserRequestDTO {
    private String name;
    private String email;
    private String password;

    public UserRequestDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
