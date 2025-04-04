package org.example.controllers.transactions;

import lombok.RequiredArgsConstructor;
import org.example.dto.TransactionRequestDTO;
import org.example.enums.TransactionCategory;
import org.example.enums.TransactionType;
import org.example.in.Reader;
import org.example.out.AddTransactionWriter;
import org.example.service.imlementations.TransactionServiceImpl;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Optional;
import org.slf4j.Logger;

@RequiredArgsConstructor
public class AddTransactionController {
    private final TransactionServiceImpl transactionService;
    private final Reader reader = new Reader();
    private final ChoosingActionController chooseActionController;
    private final AddTransactionWriter writer = new AddTransactionWriter();
    private final Logger logger = LoggerFactory.getLogger(AddTransactionController.class);

    public void add(){
        logger.info("Запрос на добавление транзакции");
        String type_amount = reader.read();
        try{
            parseAmount(type_amount);
        } catch (NumberFormatException e){
            writer.invalidInput();
            logger.info("Ошибка NumberFormatEx, заново запрос на добавление транзакции");
            add();
        }
        int amount = parseAmount(type_amount);
        TransactionType type = parseType(type_amount);

        if (checkCategory().isEmpty()) {
            logger.info("Введена неверная категория, заново");
            writer.invalidInput();
            checkCategory();
        }
        TransactionCategory category = checkCategory().get();
        writer.askDescription();
        String description = reader.read();
        TransactionRequestDTO dto = TransactionRequestDTO.builder()
                .amount(amount).type(type).description(description).category(category)
                .date(LocalDateTime.now()).build();
        transactionService.addTransaction(dto);
    }

    private TransactionType parseType(String input){
        String type = input.substring(0,1);
        if (type.equals(TransactionType.MINUS.getName())) return TransactionType.MINUS;
        else if (type.equals(TransactionType.PLUS.getName())) return TransactionType.PLUS;
        else {
            logger.info("Неверно определен тип транзакции (+/-), заново");
            writer.invalidInput();
            parseType(input);
        }
        return null;
    }
    private int parseAmount(String input){
        return Integer.parseInt(input.substring(2));
    }
    private Optional<TransactionCategory> checkCategory(){
        writer.askCategory();
        String category = reader.read();
        for (TransactionCategory transactionCategory : TransactionCategory.values()){
            if (transactionCategory.name().equals(category.toUpperCase())) return Optional.of(transactionCategory);
        }
        return Optional.empty();
    }
}
