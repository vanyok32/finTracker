package org.example.service.interfaces;

import org.example.dto.UserRequestDTO;
import org.example.dto.UserResponseDTO;
import org.example.model.User;

import java.util.UUID;

public interface UserService {
    UserResponseDTO registerUser(UserRequestDTO userRequestDTO);
    boolean loginUser(UserRequestDTO userRequestDTO);
    boolean deleteUser(UUID id);
    UserResponseDTO updateUser(UUID id, UserRequestDTO userRequestDTO);
    boolean hasUser(String email);
    UserResponseDTO getUserByEmail(String email);
    UserResponseDTO getUserById(UUID id);
    String getUserPassword(String email);

}
