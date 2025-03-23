package org.example.repositories;
import org.example.model.User;
import java.util.List;

public class UserRepository {
    private List<User> users;

    public void addUser(User user){
        users.add(user);
        System.out.println("Пользователь " + user + " добавлен ");
    }
    public void removeUser(User user){
        System.out.println("Пользователь " + user + " удален ");
        users.remove(user);
    }
    public void UpdateUser(User user, String email, String password, String name){
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        System.out.println("Данные успешно обновлены");
    }

    public List<User> getUsers() {
        System.out.println("Список всех пользователей ");
        return users;
    }


}
