package org.example.dto;
import lombok.*;
import org.example.enums.TransactionCategory;
import org.example.enums.TransactionType;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@AllArgsConstructor
public class TransactionRequestDTO {
    private double amount;
    private TransactionCategory category;
    private LocalDateTime date;
    private TransactionType type;
    private String description;

    public TransactionRequestDTO(){}
}

