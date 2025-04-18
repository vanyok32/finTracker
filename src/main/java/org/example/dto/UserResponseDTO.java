package org.example.dto;
import lombok.*;

import java.util.UUID;

@Getter
@Setter

public class UserResponseDTO {
    private UUID id;
    private String name;
    private String email;

    @Override
    public String toString(){
        return "Имя пользователя: " + name + "\nemail: " + email+ "\n ID: " + id;
    }
    public UserResponseDTO(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    public UserResponseDTO(){}


}

