package org.example.commands;

import lombok.RequiredArgsConstructor;
import org.example.enums.TransactionCategory;
import org.example.enums.TransactionType;
import org.example.in.Reader;
import org.example.out.AddTransactionWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
@RequiredArgsConstructor
public class AmountCategoryTypeParser {

    private final Logger logger = LoggerFactory.getLogger(AmountCategoryTypeParser.class);
    private final AddTransactionWriter writer;
    private final Reader reader;

    public  TransactionType parseType(String input){
        String type = input.substring(0,1);
        if (type.equals(TransactionType.MINUS.getName())) return TransactionType.MINUS;
        else if (type.equals(TransactionType.PLUS.getName())) return TransactionType.PLUS;
        else {
            logger.info("Неверно определен тип транзакции (+/-), заново");
            writer.invalidInput();
        }
        return null;
    }
    public int parseAmount(String input){
        return Integer.parseInt(input.substring(2));
    }
    public Optional<TransactionCategory> checkCategory(){
        writer.askCategory();
        String category = reader.read();
        for (TransactionCategory transactionCategory : TransactionCategory.values()){
            if (transactionCategory.name().equals(category.toUpperCase())) return Optional.of(transactionCategory);
        }
        return checkCategory();
    }
}
