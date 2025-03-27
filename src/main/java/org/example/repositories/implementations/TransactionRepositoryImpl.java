package org.example.repositories.implementations;

import org.example.model.Transaction;
import org.example.repositories.interfaces.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TransactionRepositoryImpl implements TransactionRepository {
    private List<Transaction> transactions;
    private Logger logger = LoggerFactory.getLogger(TransactionRepositoryImpl.class);

    @Override
    public Transaction addTransaction(Transaction transaction){
        logger.debug("попытка добавления транзакции");
        transactions.add(transaction);
        logger.info("транзакция добавлена, id: {}", transaction.getTransactionID());
        return transaction;
    }

    @Override
    public List<Transaction> getTransactionsByUserID (UUID UserID){
        logger.debug("попытка получения транзакции по userID");
        List<Transaction> transactionsForReturn = new ArrayList<Transaction>();
        for(Transaction transaction : transactions){
            if(transaction.getUserID().equals(UserID)){
                transactionsForReturn.add(transaction);
            }
        }
        logger.info("получено транзакций: {}", transactionsForReturn.size());
        return transactionsForReturn;
    }
    @Override
    public Optional<Transaction> getTransactionByTransactionID (UUID TransactionID){
        logger.debug("попытка получения транзакции по transactionID");
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionID().equals(TransactionID)) {
                logger.info("транзакция возвращена, ID: {}", transaction.getTransactionID());
                return Optional.of(transaction);
            }
        }
        logger.info("возвращен пустота");
        return Optional.empty();
    }
    @Override
    public boolean deleteTransaction(UUID id){
        logger.debug("попытка удаления транзакции");
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionID().equals(id)) {
                transactions.remove(transaction);
                logger.info("транзакция удалена, ID {}",id);
                return true;
            }
        }
        logger.info("Транзакция не найдена, ID: {}",id);
        return false;

    }
    @Override
    public Optional<Transaction> updateTransaction(Transaction transaction, UUID id){
        logger.debug("попытка обновления транзакции");
        for (Transaction transaction1 : transactions) {
            if (transaction1.getTransactionID().equals(id)) {
                transaction1.setType(transaction.getType());
                transaction1.setAmount(transaction.getAmount());
                transaction1.setDescription(transaction.getDescription());
                transaction1.setCategory(transaction.getCategory());
                transaction1.setDate(transaction.getDate());
                logger.info("обновлена успешно, ID: {}",id);
                return Optional.of(transaction1);
            }
        }
        logger.info("не обновлена, ID {}", id);
        return Optional.empty();
    }

}
