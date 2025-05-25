package org.example.mappers;

import org.example.dto.UserRequestDTO;
import org.example.dto.UserResponseDTO;
import org.example.model.User;

public class UserMapper {
    private final static UserMapper INSTANCE = new UserMapper();
    // User -> dto
    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getUserID());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    //dto -> user
    public User toUser(UserRequestDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        return user;
    }
    public static UserMapper getInstance() {
        return INSTANCE;
    }
    private UserMapper() {}
}
