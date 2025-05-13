package org.example.controllers.administration;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.controllers.ChoosingActionController;
import org.example.in.Reader;
import org.example.out.AdministrationWriter;
import org.example.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RequiredArgsConstructor
public class UnblockUserController {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UnblockUserController.class);
    private final AdministrationWriter administrationWriter;
    private final Reader reader;
    @Setter
    private ChoosingActionController choosingActionController;
    public void unBlockUser(){

        administrationWriter.askEmail();
        String email = reader.read();
        if (userService.unblockUser(email)){
            logger.info("пользователь разблокирован, email {}", email);
        }
        else logger.info("пользователь не разблокирован, email {}", email);
        choosingActionController.start();
    }
}
