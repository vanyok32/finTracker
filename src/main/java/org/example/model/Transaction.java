package org.example.model;
import lombok.*;
import org.example.model.enums.*;
import org.example.repositories.TransactionRepository;


import java.util.Date;

@Setter
@Getter
public class Transaction {
    private TransactionType type; //тип транзакции (доход/расход)
    private TransactionCategory category; //категория транзакции
    private Date date; //дата транзакции
    private String description; // описание
    private Double amount; //сумма транзацкии
    private int UserID; //id владельца транзакции
    private int TransactionID; //уникальное значение транзакции


    public Transaction(TransactionType type, TransactionCategory category,
                       Date date, String description, Double amount, int UserID) {
        this.type = type;
        this.category = category;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.UserID = UserID;
        this.TransactionID = (int) Math.random();
    }


}
