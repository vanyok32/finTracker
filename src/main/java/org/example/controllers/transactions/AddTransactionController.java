package org.example.controllers.transactions;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.commands.AmountCategoryTypeParser;
import org.example.controllers.ChoosingActionController;
import org.example.dto.TransactionRequestDTO;
import org.example.entrypoint.UserIdOwner;
import org.example.enums.TransactionCategory;
import org.example.enums.TransactionType;
import org.example.in.Reader;
import org.example.out.AddTransactionWriter;

import org.example.service.interfaces.TransactionService;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;


@RequiredArgsConstructor
public class AddTransactionController {
    private final TransactionService transactionService;
    private final Reader reader;
    private final AddTransactionWriter writer;
    private final Logger logger = LoggerFactory.getLogger(AddTransactionController.class);
    @Setter
    private ChoosingActionController choosingActionController;
    private final AmountCategoryTypeParser amountCategoryTypeParser;
    public void add(){
        logger.info("Запрос на добавление транзакции");
        writer.askAmount();
        String type_amount = reader.read();
        try{
            amountCategoryTypeParser.parseAmount(type_amount);
            amountCategoryTypeParser.parseType(type_amount);
        } catch (NumberFormatException | NullPointerException e){
            writer.invalidInput();
            logger.info("Ошибка NumberFormatEx, заново запрос на добавление транзакции");
            add();
        }
        int amount = amountCategoryTypeParser.parseAmount(type_amount);
        TransactionType type = amountCategoryTypeParser.parseType(type_amount);
        TransactionCategory category = amountCategoryTypeParser.checkCategory().get();
        writer.askDescription();
        String description = reader.read();
        TransactionRequestDTO dto = TransactionRequestDTO.builder()
                .amount(amount).type(type).description(description).category(category)
                .date(LocalDate.now()).build();
        logger.info("Новая транзакция добавлена");
        transactionService.addTransaction(dto, UserIdOwner.getInstance().getUserID());
        choosingActionController.start();
    }
}
