package org.example.validators;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dto.UserRequestDTO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserValidator implements Validator<UserRequestDTO> {
    private final static UserValidator INSTANCE = new UserValidator();
    public boolean validateEmail(String email){return email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");}
    public boolean validateName(String name) {
        return name.matches("^[a-zA-Zа-яА-Яё]+(?: [a-zA-Zа-яА-Яё]+)*$");
    }
    public boolean validatePassword(String password) {
        return password.matches("^(?=.*[a-z])[A-Za-z\\d]{1,16}$");
    }

    @Override
    public ValidationResult isValid(UserRequestDTO dto) {
        ValidationResult validationResult = new ValidationResult();
        if (!validateEmail(dto.getEmail())) {
            validationResult.addError(Error.of("Invalid email", "invalid.email"));
        }
        if (!validateName(dto.getName())) {
            validationResult.addError(Error.of("Invalid name", "invalid.name"));
        }
        if (!validatePassword(dto.getPassword())) {
            validationResult.addError(Error.of("Invalid password", "invalid.password"));
        }
        return validationResult;
    }

    public static UserValidator getInstance() {
        return INSTANCE;
    }
}
