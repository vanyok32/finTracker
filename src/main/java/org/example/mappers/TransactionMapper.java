package org.example.mappers;

import org.example.dto.TransactionRequestDTO;
import org.example.dto.TransactionResponseDTO;
import org.example.model.Transaction;

public class TransactionMapper {
    private final static TransactionMapper INSTANCE = new TransactionMapper();

    //Транзакция -> dto
    public TransactionResponseDTO toResponseDTO(Transaction transaction) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setTransactionID(transaction.getTransactionID());
        dto.setDescription(transaction.getDescription());
        dto.setDate(transaction.getDate());
        dto.setCategory(transaction.getCategory());
        dto.setAmount(transaction.getAmount());
        dto.setType(transaction.getType());
        dto.setUserId(transaction.getUserID());
        return dto;
    }

    //  dto -> Транзакция
    public Transaction toTransaction(TransactionRequestDTO dto) {
        Transaction tr = new Transaction();
        tr.setDescription(dto.getDescription());
        tr.setDate(dto.getDate());
        tr.setCategory(dto.getCategory());
        tr.setAmount(dto.getAmount());
        tr.setType(dto.getType());
        tr.setUserID(dto.getUserId());
        return tr;
    }
    public static TransactionMapper getInstance() {
        return INSTANCE;
    }
    private TransactionMapper() {}
}
