package org.example.controllers.transactions;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.commands.AmountCategoryTypeParser;
import org.example.controllers.ChoosingActionController;
import org.example.dto.TransactionRequestDTO;
import org.example.dto.TransactionResponseDTO;
import org.example.entrypoint.UserIdOwner;
import org.example.in.Reader;
import org.example.mappers.TransactionMapper;
import org.example.out.TransactionListPrinter;
import org.example.out.UpdateTransactionWriter;
import org.example.service.interfaces.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
@RequiredArgsConstructor
public class UpdateTransactionController {
    private final TransactionService transactionService;
    private final Reader reader;
    private final UpdateTransactionWriter writer;
    private final Logger logger = LoggerFactory.getLogger(UpdateTransactionController.class);
    private final TransactionListPrinter printer;
    private final AmountCategoryTypeParser amountCategoryTypeParser;
    @Setter
    private ChoosingActionController choosingActionController;

    public void update(){
        logger.info("Вход в функцию обновления транзакции");
        printer.print(UserIdOwner.getInstance().getUserID());
        writer.chooseTransactionById();
        UUID id = UUID.fromString(reader.read());
        TransactionResponseDTO tr = transactionService.getTransactionByID(id);
        if (tr != null) {
            logger.info("Транзакция с id: {} выведена", id);
            System.out.println(tr.toString());
            writer.askAmount();
            String type_amount = reader.read();
            try{
                amountCategoryTypeParser.parseAmount(type_amount);
                amountCategoryTypeParser.parseType(type_amount);
            } catch (NumberFormatException | NullPointerException e){
                writer.invalidInput();
                logger.info("Ошибка NumberFormatEx, заново запрос на добавление транзакции");
                update();
            }
            writer.askDescription();
            String description = reader.read();
            TransactionRequestDTO dto = TransactionRequestDTO.builder()
                    .amount(amountCategoryTypeParser.parseAmount(type_amount))
                    .type(amountCategoryTypeParser.parseType(type_amount))
                    .description(description)
                    .category(amountCategoryTypeParser.checkCategory().get())
                    .date(tr.getDate()).build();
            transactionService.updateTransaction(dto, id);
            logger.info("Транзакция с id: {} обновлена", id);
            choosingActionController.start();

        }
        else{
            logger.info("Транзакция с id: {} не найдена", id);
            writer.invalidInput();
            update();
        }
    }



}
