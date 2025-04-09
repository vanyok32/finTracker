package org.example.controllers.transactions;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.controllers.ChoosingActionController;
import org.example.entrypoint.UserIdOwner;
import org.example.in.Reader;
import org.example.out.TransactionListPrinter;
import org.example.out.UpdateTransactionWriter;
import org.example.service.imlementations.TransactionServiceImpl;
import org.example.service.interfaces.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
@RequiredArgsConstructor
public class DeleteTransactionController {
    private final TransactionService transactionService;
    private final Logger logger = LoggerFactory.getLogger(DeleteTransactionController.class);
    private final TransactionListPrinter printer;
    private final UpdateTransactionWriter writer;
    private final Reader reader;
    @Setter
    private ChoosingActionController choosingActionController;

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
        choosingActionController.start();

    }

}
