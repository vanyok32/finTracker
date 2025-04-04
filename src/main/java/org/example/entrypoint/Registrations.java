package org.example.entrypoint;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserRequestDTO;
import org.example.in.Reader;
import org.example.model.User;
import org.example.out.AuthentificationWriter;
import org.example.out.RegistrationWriter;
import org.example.repositories.implementations.TransactionRepositoryImpl;
import org.example.service.interfaces.UserService;
import org.example.validators.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class Registrations {

    private final RegistrationWriter writer = new RegistrationWriter();
    private final Reader reader = new Reader();
    private final UserService service;
    private final Logger logger = LoggerFactory.getLogger(Registrations.class);
    private final UserValidator validator = new UserValidator();

    public void registrate(String email){
        logger.info("Пользователь пытается зарегистрироваться");
        writer.askName();
        String name = reader.read();
        if (!validator.validateName(name)){
            logger.info("Пользователь ввел некорректное имя, заново");
            writer.invalidName();
            registrate(email);
        }
        writer.askPassword();
        String password = reader.read();
        if (!validator.validatePassword(password)){
            writer.invalidValidatePassword();
            logger.info("Пользователь ввел неверный пароль, заново");
            registrate(email);
        }
        writer.askPasswordAgain();
        String passwordAgain = reader.read();
        if (!password.equals(passwordAgain)){
            writer.invalidPassword();
            logger.info("пароли не совпадают {} {}, попытка заново", password, passwordAgain);
            registrate(email);
        }
        UserRequestDTO user = new UserRequestDTO(name, email, password);
        logger.info("пользователь прошел регистрацию, заносится в репозиторий");
        service.loginUser(user);
        UserIdOwner.getInstance().setUserID(service.getUserByEmail(email).getUserID());
        /**
         * меню
         */
    }
}
