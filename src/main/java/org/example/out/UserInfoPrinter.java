package org.example.out;

import lombok.RequiredArgsConstructor;
import org.example.model.User;
import org.example.service.interfaces.UserService;

import java.util.UUID;

@RequiredArgsConstructor
public class UserInfoPrinter {
    private final UserService userService;

    public void print(UUID userID) {
        User user = userService.getUserById(userID);
        System.out.println(user.toString());
    }

}
