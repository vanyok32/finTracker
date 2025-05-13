package org.example.service.imlementations;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserRequestDTO;
import org.example.dto.UserResponseDTO;
import org.example.enums.UserRole;
import org.example.enums.UserStatus;
import org.example.exeptions.UserNotFoundException;
import org.example.mappers.UserMapper;
import org.example.model.User;
import org.example.repositories.implementations.UserRepositoryImpl;
import org.example.repositories.interfaces.UserRepository;
import org.example.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

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
    public boolean deleteUser(UUID id) {
        logger.debug("удаление в UserService");
        return userRepository.removeUser(id);
    }
    @Override
    public UserResponseDTO updateUser(UUID id, UserRequestDTO userRequestDTO) {
        logger.info("обновление в UserService");
        User user = userMapper.toUser(userRequestDTO);
        user.setUserID(id);
        userRepository.updateUser(user, id);
        return userMapper.toResponseDTO(user);
    }

    @Override
    public boolean hasUser(String email) {
        return userRepository.getUserByEmail(email).isPresent();
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        return userMapper.toResponseDTO(userRepository.getUserByEmail(email).orElseThrow(()
                -> new UserNotFoundException(email)));
    }

    @Override
    public UserResponseDTO getUserById(UUID id) {
        return userMapper.toResponseDTO(userRepository.getUserByID(id).orElseThrow(()
                -> new UserNotFoundException(id.toString())));
    }

    @Override
    public String getUserPassword(String email) {
        User user = userRepository.getUserByEmail(email).get();
        return user.getPassword();
    }
    @Override
    public boolean blockUser(String email) {
        return userRepository.blockUser(email);
    }

    @Override
    public boolean unblockUser(String email) {
        return userRepository.unBlockUser(email);
    }

    @Override
    public UserStatus getUserStatus(String email) {
        return userRepository.getUserStatus(email);
    }

    @Override
    public UserRole getUserRole(String email) {
        return userRepository.getUserRole(email);
    }
}
