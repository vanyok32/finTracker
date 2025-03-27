package org.example.service.interfaces;

import org.example.dto.TransactionRequestDTO;
import org.example.dto.TransactionResponseDTO;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    public TransactionResponseDTO addTransaction(TransactionRequestDTO transactionRequestDTO);
    public void deleteTransaction(UUID id);
    public TransactionResponseDTO updateTransaction(TransactionRequestDTO transactionRequestDTO, UUID id);
    public TransactionResponseDTO getTransactionByID(UUID id);
    public List<TransactionResponseDTO> getTransactionsByUserId(UUID userId);
}
