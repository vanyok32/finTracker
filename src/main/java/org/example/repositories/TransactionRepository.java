package org.example.repositories;

import org.example.model.Transaction;
import org.example.model.User;

import java.util.List;

public class TransactionRepository {
    private List<Transaction> transactions;

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
        System.out.println("Транзакция успешно добавлена");//todo исключения
    }

    public Transaction getTransactions (int UserID){
        for(Transaction transaction : transactions){
            if(transaction.getUserID() == UserID){
                return transaction;
            }
        }
        return null; //TODO добавить исключения!

    }
    public void deleteTransaction(int UserID){}

}
