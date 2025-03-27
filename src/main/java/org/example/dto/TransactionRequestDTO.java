package org.example.dto;
import lombok.*;
import org.example.model.enums.TransactionCategory;
import org.example.model.enums.TransactionType;

import java.time.LocalDateTime;
@Getter
@Setter
public class TransactionRequestDTO {
    private double amount;
    private TransactionCategory category;
    private LocalDateTime date;
    private TransactionType type;
    private String description;
}
