package org.example.controllers.transactions;

import lombok.RequiredArgsConstructor;
import org.example.dto.TransactionResponseDTO;
import org.example.entrypoint.UserIdOwner;
import org.example.in.Reader;
import org.example.mappers.TransactionMapper;
import org.example.out.TransactionListPrinter;
import org.example.out.UpdateTransactionWriter;
import org.example.service.imlementations.TransactionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
@RequiredArgsConstructor
public class UpdateTransactionController {
    private final TransactionServiceImpl transactionService;
    private final Reader reader = new Reader();
    private final ChoosingActionController chooseActionController;
    private final UpdateTransactionWriter writer = new UpdateTransactionWriter();
    private final Logger logger = LoggerFactory.getLogger(UpdateTransactionController.class);
    private final TransactionMapper mapper = new TransactionMapper();
    private final TransactionListPrinter printer = new TransactionListPrinter(transactionService);


    public void update(){
        logger.info("Вход в функцию обновления транзакции");
        printer.print(UserIdOwner.getInstance().getUserID());
        writer.chooseTransactionById();
        UUID id = UUID.fromString(reader.read());
        TransactionResponseDTO tr = transactionService.getTransactionByID(id);
        if (tr != null) {
            logger.info("Транзакция с id: {} выведена", id);
            System.out.println(mapper.toString(tr));
        }
        else{
            logger.info("Транзакция с id: {} не найдена", id);
            writer.invalidInput();
            update();
        }
    }



}
