package org.example.repositories.interfaces;

import org.example.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    public User addUser(User user);
    public Optional<User> updateUser(User user, UUID id);
    public boolean removeUser(UUID id);
    public Optional<User> getUserByID(UUID id);
    public List<User> getUsers();
}
