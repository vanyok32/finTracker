package org.example.controllers.user;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.controllers.ChoosingActionController;
import org.example.entrypoint.Identification;
import org.example.entrypoint.UserIdOwner;
import org.example.in.Reader;
import org.example.out.DeleteUserWriter;
import org.example.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@RequiredArgsConstructor

public class DeleteUserController {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(DeleteUserController.class);
    private final DeleteUserWriter writer;
    private final Reader reader;
    private final Identification identification;
    @Setter
    private ChoosingActionController choosingActionController;
    public void delete(){
        UUID id = UserIdOwner.getInstance().getUserID();
        logger.info("пользователь вошел в удаление профиля, ID {}", id);
        String yes = "YES";

        writer.confirmDelete();
        if (reader.read().toUpperCase().equals(yes)) {
            logger.info("аккаунт с id: {} удален", id);
            userService.deleteUser(id);
            identification.start();
        }
        else {
            choosingActionController.start();
        }
    }
}
