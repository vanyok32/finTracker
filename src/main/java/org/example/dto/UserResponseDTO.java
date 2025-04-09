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

}

