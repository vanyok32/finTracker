package org.example.controllers.user;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.controllers.ChoosingActionController;
import org.example.dto.UserRequestDTO;
import org.example.entrypoint.UserIdOwner;
import org.example.in.Reader;
import org.example.model.User;
import org.example.out.UpdateUserWriter;
import org.example.out.UserInfoPrinter;
import org.example.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor

public class UpdateUserController {
    private final UserService userService;
    private final Reader reader;
    private final UpdateUserWriter writer;
    private final UserInfoPrinter printer;
    private final Logger logger = LoggerFactory.getLogger(UpdateUserController.class);
    @Setter
    private ChoosingActionController choosingActionController;
    public void update(){
        printer.print(UserIdOwner.getInstance().getUserID());
        writer.askName();
        String newName = reader.read();
        writer.askEmail();
        String newEmail = reader.read();
        writer.askPassword();
        String newPassword = reader.read();
        writer.askPassword();
        String newPassword2 = reader.read();
        if (!newPassword.equals(newPassword2)) {
            logger.info("Введенные пароли не совпадают {} : {}", newPassword, newPassword2);
            writer.invalidPasswords();
            update();
        }
        logger.info("Пользователь успешно обновил данные {}, {},{}", newName, newEmail, newPassword);
        UserRequestDTO dto  = new UserRequestDTO(newName, newEmail, newPassword);
        userService.updateUser(UserIdOwner.getInstance().getUserID(), dto);
        choosingActionController.start();
    }
}
