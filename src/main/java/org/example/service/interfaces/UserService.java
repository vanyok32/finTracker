package org.example.service.interfaces;

import org.example.dto.UserRequestDTO;
import org.example.dto.UserResponseDTO;

import java.util.UUID;

public interface UserService {
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO);
    public boolean loginUser(UserRequestDTO userRequestDTO);
    public void deleteUser(UUID id);
    public UserResponseDTO updateUser(UUID id, UserRequestDTO userRequestDTO);

}
