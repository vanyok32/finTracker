package org.example.service.implementations;

import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.UserRequestDTO;
import org.example.dto.UserResponseDTO;
import org.example.enums.UserRole;
import org.example.enums.UserStatus;
import org.example.exceptions.UserNotFoundException;
import org.example.exceptions.ValidationException;
import org.example.mappers.UserMapper;
import org.example.model.User;
import org.example.repositories.implementations.UserRepositoryImpl;
import org.example.repositories.interfaces.UserRepository;
import org.example.service.interfaces.UserService;
import org.example.validators.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();
    private final static UserServiceImpl INSTANCE = new UserServiceImpl();
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserValidator userValidator = UserValidator.getInstance();

    @Override
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        var result = userValidator.isValid(userRequestDTO);
        if (!result.isValid()) {
            throw new ValidationException(result.getErrors());
        }
        logger.debug("регистрация пользователя в UserService");
        User user = userRepository.addUser(userMapper.toUser(userRequestDTO));
        return userMapper.toResponseDTO(user);
    }

    @Override
    public boolean loginUser(String email , String pwd) {
        logger.debug("попытка авторизации в UserService");
        return userRepository.getUsers().stream()
                .anyMatch(u ->
                        u.getEmail().equals(email) &&
                                u.getPassword().equals(pwd));
    }

    @Override
    public boolean deleteUser(UUID id) {
        logger.debug("удаление в UserService");
        if (!userRepository.removeUser(id)){
            throw new UserNotFoundException(id.toString());
        }
        return true;
    }

    @Override
    public UserResponseDTO updateUser(UUID id, UserRequestDTO userRequestDTO) {
        logger.info("обновление в UserService");
        var result = userValidator.isValid(userRequestDTO);
        if (!result.isValid()) {
            throw new ValidationException(result.getErrors());
        }
        User user = userMapper.toUser(userRequestDTO);
        user.setUserID(id);
        if (!userRepository.updateUser(user, id)){
            throw new UserNotFoundException("User not found");
        }
        return userMapper.toResponseDTO(user);
    }

    @Override
    public boolean hasUser(String email) {
        if (!userRepository.getUserByEmail(email).isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        return true;
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
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
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
        if (userRepository.getUserStatus(email) == null) throw new UserNotFoundException(email);
        return userRepository.getUserStatus(email);
    }

    @Override
    public UserRole getUserRole(String email) {
        if (userRepository.getUserRole(email) == null) throw new UserNotFoundException(email);
        return userRepository.getUserRole(email);
    }

    @Override
    public List<User> getAllUsers() { return userRepository.getUsers(); }

    public static UserServiceImpl getInstance(){
        return INSTANCE;
    }
    private UserServiceImpl(){};
}
