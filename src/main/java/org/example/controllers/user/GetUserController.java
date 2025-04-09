package org.example.controllers.user;

import lombok.RequiredArgsConstructor;
import org.example.entrypoint.UserIdOwner;
import org.example.in.Reader;
import org.example.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RequiredArgsConstructor

public class GetUserController {
    private final Logger logger = LoggerFactory.getLogger(GetUserController.class);
    private final UserService userService;

    public void get(){
        logger.info("Вывод информации о пользователе с id: {}", UserIdOwner.getInstance().getUserID());
        System.out.println(userService.getUserById(UserIdOwner.getInstance().getUserID()).toString());
    }

}
