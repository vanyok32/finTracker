package org.example.controllers.transactions;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.controllers.ChoosingActionController;
import org.example.entrypoint.UserIdOwner;
import org.example.in.Reader;
import org.example.out.GetTransactionWriter;

import org.example.service.interfaces.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
@RequiredArgsConstructor

public class GetTransactionController {
    private final TransactionService transactionService;
    private final GetTransactionWriter writer;
    private final Logger logger = LoggerFactory.getLogger(GetTransactionController.class);
    @Setter
    private ChoosingActionController actionController;
    private final Reader reader;
    public void get(){
        writer.allOrOne();
        String answer = reader.read();
        if (answer.equalsIgnoreCase("all")) {
            logger.info("Выводятся все транзакции");
            System.out.println(transactionService.getTransactionsByUserId(UserIdOwner.getInstance().getUserID()));
            actionController.start();
        }
        else if (answer.equalsIgnoreCase("exit")) {
            actionController.start();
            return;
        }
        else{
            try{

                logger.info("Выводится информация о транзакции с id {}", answer);
                System.out.println(transactionService.getTransactionByID(UUID.fromString(answer)).toString());
                actionController.start();
            }catch (NullPointerException | IllegalArgumentException e){
                writer.transactionNotFound();
                get();
                return;
            }

        }
    }

}