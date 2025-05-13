package org.example.repositories.interfaces;

import org.example.enums.UserRole;
import org.example.enums.UserStatus;
import org.example.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    public User addUser(User user);
    public boolean updateUser(User user, UUID id);
    public boolean removeUser(UUID id);
    public Optional<User> getUserByID(UUID id);
    public Optional<User> getUserByEmail(String email);
    public List<User> getUsers();
    boolean blockUser(String email);
    boolean unBlockUser(String email);
    UserStatus getUserStatus(String email);
    UserRole getUserRole(String email);
}
