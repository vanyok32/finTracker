package org.example.validators;

public class UserValidator {

    public boolean validateEmail(String email){return email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");}
    public boolean validateName(String name) {
        return name.matches("^[a-zA-Zа-яА-Яё]+(?: [a-zA-Zа-яА-Яё]+)*$");
    }
    public boolean validatePassword(String password) {
        return password.matches("^(?=.*[a-z])[A-Za-z\\d]{1,16}$");
    }

}
