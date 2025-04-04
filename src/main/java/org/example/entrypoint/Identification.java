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
    private final Reader reader = new Reader();
    private final Authentication authentication;//= new Authentication(userService);
    private final Registrations registrations;// = new Registrations(userService);
    private final Logger logger = LoggerFactory.getLogger(Identification.class);
    private final IdentificationWriter writer = new IdentificationWriter();
    private final UserValidator validator = new UserValidator();

    public Identification(UserService userService){
        this.userService = userService;
        this.authentication = new Authentication(userService);
        this.registrations = new Registrations(userService);
    }
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
