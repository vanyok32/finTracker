package org.example.model;
import lombok.*;
import org.example.entrypoint.UserIdOwner;
import org.example.enums.TransactionCategory;
import org.example.enums.TransactionType;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Setter
@Getter
public class Transaction {
    private TransactionType type; //тип транзакции (доход/расход)
    private TransactionCategory category; //категория транзакции
    private LocalDateTime date; //дата транзакции
    private String description; // описание
    private Double amount; //сумма транзакции
    private UUID UserID; //id владельца транзакции
    private UUID TransactionID; //уникальное значение транзакции

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Transaction(TransactionType type, TransactionCategory category,
                        String description, Double amount) {

        this.type = type;
        this.category = category;
        this.date = LocalDateTime.now();
        this.description = description;
        this.amount = amount;

    }
    public Transaction() {}
}
