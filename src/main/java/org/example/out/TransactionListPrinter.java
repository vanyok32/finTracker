package org.example.out;

import lombok.RequiredArgsConstructor;
import org.example.dto.TransactionResponseDTO;
import org.example.mappers.TransactionMapper;
import org.example.service.imlementations.TransactionServiceImpl;

import java.util.List;
import java.util.UUID;
@RequiredArgsConstructor
public class TransactionListPrinter {
    private final TransactionServiceImpl transactionService;
    UpdateTransactionWriter writer = new UpdateTransactionWriter();
    TransactionMapper mapper = new TransactionMapper();

    public void print(UUID userID) {
        List<TransactionResponseDTO> trList = transactionService.getTransactionsByUserId(userID);
        writer.showTransactions();
        System.out.println(trList.stream().map(mapper::toString));
    }
}
