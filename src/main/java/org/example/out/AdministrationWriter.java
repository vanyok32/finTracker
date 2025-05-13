package org.example.out;

public class AdministrationWriter
{
    public void askEmail(){
        System.out.println("Введите email пользователя");
    }


    public void adminActions(){
        System.out.println("""
                Вы также являетесь администратором, вам доступны команды
                block/unblock user""");
    }
}
