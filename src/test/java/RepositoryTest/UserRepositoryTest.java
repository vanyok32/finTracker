package RepositoryTest;


import org.example.model.User;
import org.example.repositories.implementations.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

class UserRepositoryTest {
    private UserRepositoryImpl repository;
    private User user;
    private User user2;

    @BeforeEach
    void setUp() {
        repository = new UserRepositoryImpl();
        user = new User("Беженарь Тимур Кафкович", "ilovegym@mail.ru", "kafka", UUID.randomUUID());
        user2 = new User("Хибернейтов Семен", "lox@xyi.com", "rocket_league123", UUID.randomUUID());

    }
    @Test
    @DisplayName("Успешное добавление пользователя")
    void addUser_ValidUser_returnUser(){
        User new_user = repository.addUser(user);
        Assertions.assertNotNull(new_user);
        Assertions.assertEquals(user.getUserID(), new_user.getUserID());
        Assertions.assertEquals(user.getName(), new_user.getName());

    }
    @Test
    @DisplayName("успешное|ошибочное обновление пользователя")
    void updateUser_returnOptional(){
        repository.addUser(user);
        Optional<User> updated_user1 = repository.updateUser(user2, user.getUserID());
        Optional<User> updated_user2 = repository.updateUser(user2, UUID.randomUUID());
        Assertions.assertTrue(updated_user2.isEmpty());
        Assertions.assertTrue(updated_user1.isPresent());
        Assertions.assertEquals(user.getUserID(), updated_user1.get().getUserID());
        Assertions.assertEquals(user2.getName(), updated_user1.get().getName());
    }
    @Test
    @DisplayName("успешное/ошибочное удаление пользователя")
    void removeUser_TrueOrFalse(){
        repository.addUser(user);
        boolean success = repository.removeUser(user.getUserID());
        boolean fail = repository.removeUser(UUID.randomUUID());
        Assertions.assertTrue(success);
        Assertions.assertFalse(fail);
    }
    @Test
    @DisplayName("успешный/ошибочный поиск пользователя по Id")
    void findUserByID_returnOptional(){
        repository.addUser(user);
        Optional<User> finded_user = repository.getUserByID(user.getUserID());
        Optional<User> not_finded_user = repository.getUserByID(UUID.randomUUID());
        Assertions.assertTrue(finded_user.isPresent());
        Assertions.assertTrue(not_finded_user.isEmpty());
        Assertions.assertEquals(user.getUserID(), finded_user.get().getUserID());
    }
    @Test
    @DisplayName("успешное/ошибочное получения пользователя по email")
    void findUserByEmail_returnOptional(){
        repository.addUser(user2);
        Optional<User> finded_user = repository.getUserByEmail(user2.getEmail());
        Optional<User> not_fided_user = repository.getUserByEmail("empty@mail.ru");
        Assertions.assertTrue(finded_user.isPresent());
        Assertions.assertTrue(not_fided_user.isEmpty());
        Assertions.assertEquals(user2.getUserID(), finded_user.get().getUserID());
    }

}
