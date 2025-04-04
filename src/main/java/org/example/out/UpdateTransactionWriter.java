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
}
