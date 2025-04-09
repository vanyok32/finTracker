package org.example.out;

public class IdentificationWriter {
    public void writeGreeting(){
        System.out.println("""
                Добро пожаловать, это Ваш личный финансовый трекер.
                     Для дальнейших действий введите ваш email""");
    }
    public void invalidEmail(){
        System.out.println("Вы ввели неправильный email, попробуйте еще раз!");
    }
    public void goToAuthentication(){
        System.out.println("Пользователь с таким email существует, направляем на аутентификацию!");
    }
    public void goToRegistration(){
        System.out.println("Пользователя с таким email не существует, направляем на регистрацию!");
    }
}
