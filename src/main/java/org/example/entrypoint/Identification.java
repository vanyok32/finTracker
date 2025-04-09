package org.example.entrypoint;

import lombok.RequiredArgsConstructor;
import org.example.in.Reader;

import org.example.out.IdentificationWriter;

import org.example.service.interfaces.UserService;
import org.example.validators.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class Identification {
    private final UserService userService;
    private final Reader reader;
    private final Authentication authentication;
    private final Registrations registrations;
    private final Logger logger = LoggerFactory.getLogger(Identification.class);
    private final IdentificationWriter writer;
    private final UserValidator validator;

    public void start(){
        writer.writeGreeting();
        String email = reader.read();
        if (validator.validateEmail(email)) {
            identificate(email);
        }
        else{
            logger.info("введен невалидный email, повторный запрос");
            writer.invalidEmail();
            start();
        }

    }

    public void identificate(String email){
        logger.info("пользователь идентифицируется");
        if (userService.hasUser(email)) {
            /**
             * if User blocked на будущее
                */
            logger.info("такой пользователь есть, направляем на аутентификацию");
            writer.goToAuthentication();
            authentication.login(email);

        }
        else{
            logger.info("направляем на регистрацию");
            writer.goToRegistration();
            registrations.registrate(email);

        }
    }
}
