package org.example.out;

import java.util.UUID;

public class UpdateTransactionWriter {
    public void showTransactions(){
        System.out.println("Ваши транзакции: ");
    }
    public void chooseTransactionById(){
        System.out.println("Выберите транзакцию для обновления и напишите ее ID");
    }
    public void invalidInput(){
        System.out.println("Введены неверные данные, попробуйте еще раз");
    }
    public void askCategory(){
        System.out.println("Введите категорию");
    }
    public void askAmount(){
        System.out.println("Введите сумму");
    }
    public void askName(){
        System.out.println("Введите имя пользователя");
    }
    public void askDescription(){
        System.out.println("Введите короткое описание транзакции");
    }

}
