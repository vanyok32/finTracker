package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.TransactionCategory;
import org.example.enums.TransactionType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class TransactionResponseDTO {
    private double amount;
    private TransactionCategory category;
    private LocalDate date;
    private TransactionType type;
    private String description;
    private UUID TransactionID;
    private UUID userId;
    @Override
    public String toString() {
        return this.type.getName() + this.getAmount() + ", категория: " + this.getCategory() + ", Дата: " + this.getDate()+"\n"
                 + "Описание: "+this.getDescription() + "\n TransactionID=" + this.getTransactionID();
    }
    public TransactionResponseDTO() {}

}

