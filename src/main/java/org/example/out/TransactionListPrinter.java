package org.example.out;

import lombok.RequiredArgsConstructor;
import org.example.dto.TransactionResponseDTO;
import org.example.mappers.TransactionMapper;
import org.example.service.interfaces.TransactionService;

import java.util.List;
import java.util.UUID;
@RequiredArgsConstructor
public class TransactionListPrinter {
    private final TransactionService transactionService;
    private final UpdateTransactionWriter writer;


    public void print(UUID userID) {
        List<TransactionResponseDTO> trList = transactionService.getTransactionsByUserId(userID);
        writer.showTransactions();
        for (TransactionResponseDTO tr : trList) {
            System.out.println(tr.toString());
        }
    }
}
