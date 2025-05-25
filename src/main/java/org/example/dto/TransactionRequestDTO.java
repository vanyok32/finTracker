package org.example.dto;
import lombok.*;
import org.example.enums.TransactionCategory;
import org.example.enums.TransactionType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TransactionRequestDTO {
    private double amount;
    private TransactionCategory category;
    private LocalDate date;
    private TransactionType type;
    private String description;
    private UUID userId;

    public TransactionRequestDTO(){}
}

