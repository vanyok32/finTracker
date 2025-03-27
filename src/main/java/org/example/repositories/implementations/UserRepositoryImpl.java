package org.example.repositories.implementations;
import org.example.model.User;
import org.example.repositories.interfaces.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserRepositoryImpl implements UserRepository {
    private List<User> users;

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Override
    public User addUser(User user) {
        logger.debug("попытка добавить пользователя");
        users.add(user);
        logger.info("пользователь {}  добавлен", user.getName());
        return user;
    }

    @Override
    public Optional<User> updateUser(User user, UUID id) {
        logger.debug("попытка обновить данные пользователя");
        for (User u : users) {
            if (id.equals(u.getUserID())){
                u.setName(user.getName());
                u.setEmail(user.getEmail());
                u.setPassword(user.getPassword());
                logger.info("пользователь {} обновлен", user.getName());
                return Optional.of(u);
            }
        }
        logger.info("пользователь {} не обновлен", user.getName());
        return Optional.empty();
    }

    @Override
    public boolean removeUser(UUID id) {
        logger.debug("попытка удаления пользователя");
        for (User user : users) {
            if(user.getUserID().equals(id)){
                users.remove(user);
                logger.info("пользователь {} удален", user.getName());
                return true;
            }
        }
        logger.info("пользователь не удален");
        return false;
    }

    @Override
    public Optional<User> getUserByID(UUID id) {
        logger.debug("попытка получения пользователя по ID");
        for (User user : users) {
            if(user.getUserID().equals(id)){
                logger.info("пользователь {} найден", user.getName());
                return Optional.of(user);
            }
        }
        logger.info("пользователь с id: {} не найден", id);
        return Optional.empty();
    }

    @Override
    public List<User> getUsers() {
        logger.info("возвращается список всех пользователей");
        return users;
    }
}
