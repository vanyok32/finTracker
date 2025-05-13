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
public class BlockUserController {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(BlockUserController.class);
    private final AdministrationWriter administrationWriter;
    private final Reader reader;
    @Setter
    private ChoosingActionController choosingActionController;
    public void blockUser(){

        administrationWriter.askEmail();
        String email = reader.read();
        if (userService.blockUser(email)){
            logger.info("пользователь заблокирован, email {}", email);
        }
        else logger.info("пользователь не заблокировал, email {}", email);
        choosingActionController.start();
    }
}
