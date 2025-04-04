package org.example.controllers.transactions;

import lombok.RequiredArgsConstructor;
import org.example.entrypoint.UserIdOwner;
import org.example.in.Reader;
import org.example.out.TransactionListPrinter;
import org.example.out.UpdateTransactionWriter;
import org.example.service.imlementations.TransactionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
@RequiredArgsConstructor
public class deleteTransactionController {
    private final TransactionServiceImpl transactionService;
    private final Logger logger = LoggerFactory.getLogger(deleteTransactionController.class);
    private final TransactionListPrinter printer = new TransactionListPrinter(transactionService);
    private final UpdateTransactionWriter writer = new UpdateTransactionWriter();
    private final Reader reader = new Reader();

    public void delete(){
        logger.info("Процесс удаления транзакции");
        UUID trId = UUID.fromString(reader.read());
        printer.print(UserIdOwner.getInstance().getUserID());
        if (transactionService.getTransactionByID(trId) == null) {
            writer.invalidInput();
            logger.info("Неверное ID для удаления: {}", trId);
            delete();
        }
        transactionService.deleteTransaction(trId);
        logger.info("Транзакция удалена, ID: {}", trId);
    }

}
