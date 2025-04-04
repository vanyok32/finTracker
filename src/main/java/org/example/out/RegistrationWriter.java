package org.example.out;

public class RegistrationWriter {
     public void askPassword(){
         System.out.println("Придумайте пароль");
     }
    public void askPasswordAgain(){
        System.out.println("Подтвердите пароль");
    }
    public void askName(){
        System.out.println("Введите имя пользователя");
    }
    public void invalidPassword(){
        System.out.println("""
                Даже не знаю, дядя Корней, сегодня вообще загадочный день" +
                    то пароли не совпадают, то имя еще раз вводить
                """);
    }
    public void invalidValidatePassword(){
        System.out.println("Пароль содержит запрещенные символы");
    }
    public void invalidName(){
        System.out.println("Имя пользователя содержит запрещенные символы");
    }

}
