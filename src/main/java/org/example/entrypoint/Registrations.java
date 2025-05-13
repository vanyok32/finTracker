package org.example.entrypoint;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.controllers.ChoosingActionController;
import org.example.dto.UserRequestDTO;
import org.example.enums.UserRole;
import org.example.in.Reader;
import org.example.mappers.UserMapper;
import org.example.out.RegistrationWriter;
import org.example.service.interfaces.UserService;
import org.example.validators.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class Registrations {

    private final RegistrationWriter writer;
    private final Reader reader;
    private final UserService service;
    private final Logger logger = LoggerFactory.getLogger(Registrations.class);
    private final UserValidator validator;
    private final UserMapper userMapper;

    @Setter
    private ChoosingActionController choosingActionController;
    public void registrate(String email){
        logger.info("Пользователь пытается зарегистрироваться");
        writer.askName();
        String name = reader.read();
        if (!validator.validateName(name)){
            logger.info("Пользователь ввел некорректное имя, заново");
            writer.invalidName();
            registrate(email);
            return;
        }
        writer.askPassword();
        String password = reader.read();
        if (!validator.validatePassword(password)){
            writer.invalidValidatePassword();
            logger.info("Пользователь ввел неверный пароль, заново");
            registrate(email);
            return;
        }
        writer.askPasswordAgain();
        String passwordAgain = reader.read();
        if (!password.equals(passwordAgain)){
            writer.invalidPassword();
            logger.info("пароли не совпадают {} {}, попытка заново", password, passwordAgain);
            registrate(email);
            return;
        }
        UserRequestDTO user = new UserRequestDTO(name, email, password);
        logger.info("пользователь прошел регистрацию, заносится в репозиторий");
        service.registerUser(user);
        UserIdOwner.getInstance().setUserID(service.getUserByEmail(email).getId());
        UserRoleOwner.getInstance().setRole(UserRole.USER);
        choosingActionController.start();


    }
}
