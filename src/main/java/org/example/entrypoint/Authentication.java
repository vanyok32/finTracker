package org.example.entrypoint;

import lombok.RequiredArgsConstructor;
import org.example.in.Reader;
import org.example.mappers.UserMapper;
import org.example.out.AuthentificationWriter;
import org.example.out.RegistrationWriter;
import org.example.repositories.implementations.TransactionRepositoryImpl;
import org.example.repositories.implementations.UserRepositoryImpl;
import org.example.service.imlementations.UserServiceImpl;
import org.example.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor

public class Authentication{
    private final AuthentificationWriter writer = new AuthentificationWriter();
    private final Reader reader = new Reader();
    private final UserService service;
    private final Logger logger = LoggerFactory.getLogger(Authentication.class);


    public void login(String email){
        logger.info("Пользователь пытается войти в аккаунт");
        writer.askPassword();
        String password = reader.read();
        if (password.equals(service.getUserByEmail(email).getPassword())){
            logger.info("Пользователь успешно вошел в аккаунт");
            UserIdOwner.getInstance().setUserID(service.getUserByEmail(email).getUserID());
            writer.sucsessLogin();
            /**
             * меню команд
             */
        }
        else {
            logger.info("Ошибка входа в аккаунт, попытка заново");
            writer.invalidLogin();
            login(email);
        }
    }
}
