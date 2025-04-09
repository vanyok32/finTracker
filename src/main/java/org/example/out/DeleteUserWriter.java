package org.example.out;

public class DeleteUserWriter {
    public void confirmDelete(){
        System.out.println("Вы действительно хотите удалить аккаунт? \n" +
                "Напишите No или Yes");
    }
    public void invalidInput(){
        System.out.println("Неверный ввод, вы возвращены на этап выбора действий");
    }
}
