package org.example.dto;
import lombok.*;
import org.example.model.enums.TransactionCategory;
import org.example.model.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TransactionResponseDTO {
    private double amount;
    private TransactionCategory category;
    private LocalDateTime date;
    private TransactionType type;
    private String description;
    private UUID TransactionID;
}
