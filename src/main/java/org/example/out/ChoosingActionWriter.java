package org.example.out;

public class ChoosingActionWriter {
    public void actions(){
        System.out.println("""
                 Выберите действие: 
                update/delete/get user
                add/update/delete/get transaction
                Для выхода введите exit
                """);
    }
    public void invalidInput(){
        System.out.println("Введены неверные данные");
    }

}
