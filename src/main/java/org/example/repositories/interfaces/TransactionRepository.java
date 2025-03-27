package org.example.repositories.interfaces;

import org.example.model.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {

    public Transaction addTransaction(Transaction transaction);
    public boolean deleteTransaction(UUID id);
    public Optional<Transaction> getTransactionByTransactionID(UUID id);
    public List<Transaction> getTransactionsByUserID(UUID userID);
    public Optional<Transaction> updateTransaction(Transaction transaction, UUID id);
}
