package org.example.service.imlementations;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserRequestDTO;
import org.example.dto.UserResponseDTO;
import org.example.mappers.UserMapper;
import org.example.model.User;
import org.example.repositories.implementations.UserRepositoryImpl;
import org.example.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepositoryImpl userRepository;
    private final UserMapper userMapper;

    Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Override
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        logger.debug("регистрация пользователя в UserService");
        User user = userRepository.addUser(userMapper.toUser(userRequestDTO));
        return userMapper.toResponseDTO(user);
    }
    @Override
    public boolean loginUser(UserRequestDTO userRequestDTO) {
        logger.debug("попытка авторизации в UserService");
        User user = userMapper.toUser(userRequestDTO);
        for (User u : userRepository.getUsers()){
            if (user.getEmail().equals(u.getEmail()) && user.getPassword().equals(u.getPassword())) return true;
        }
        return false;
    }
    @Override
    public void deleteUser(UUID id) {
        logger.debug("удаление в UserService");
        userRepository.removeUser(id);
    }
    @Override
    public UserResponseDTO updateUser(UUID id, UserRequestDTO userRequestDTO) {
        logger.info("обновление в UserService");
        User user = userMapper.toUser(userRequestDTO);
        user.setUserID(id);
        userRepository.updateUser(user, id);
        return userMapper.toResponseDTO(user);
    }
}
