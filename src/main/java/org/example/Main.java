package org.example;

import org.example.model.Transaction;
import org.example.model.User;
import org.example.model.enums.TransactionCategory;
import org.example.model.enums.TransactionType;

import java.sql.Date;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
    User user = new User("IVAn", "trroooro", "wert");
    Transaction transaction = new Transaction(TransactionType.MINUS, TransactionCategory.CASH, "@33",123.33,user.getUserID());

        System.out.println(transaction.getTransactionID());
        System.out.println(user.getUserID());

        /*todo
            добавить логирование (в logback и pom уже инициализировал)
        реализовать ебучий TransactionService!!!!! - сделал

            
         */
    }
}