package RepositoryTest;

import org.example.entrypoint.UserIdOwner;
import org.example.enums.TransactionCategory;
import org.example.enums.TransactionType;
import org.example.model.Transaction;
import org.example.repositories.implementations.TransactionRepositoryImpl;
import org.example.repositories.interfaces.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class TransactionRepositoryTest {
    private TransactionRepository repository;
    private Transaction transaction1;
    private Transaction transaction2;


    @BeforeEach
    void setUp() {
        repository = new TransactionRepositoryImpl();
        transaction1 = new Transaction(TransactionType.MINUS, 2222.00, TransactionCategory.CASH,
                "дал бройлеру на ростмасер");
        transaction2 = new Transaction(TransactionType.PLUS, 5000.55, TransactionCategory.PEOPLE,
                "Перевод от бройлера за ростмастер");
        UserIdOwner.getInstance().setUserID(UUID.randomUUID());
    }

    @Test
    @DisplayName("успешное добавление транзакции")
    void addTransaction_ValidTransaction_returnTransaction() {
        Transaction added_transaction = repository.addTransaction(transaction1);
        Assertions.assertNotNull(added_transaction);
        Assertions.assertEquals(transaction1.getTransactionID(), added_transaction.getTransactionID());
        Assertions.assertEquals(transaction1.getCategory(), added_transaction.getCategory());
        Assertions.assertEquals(transaction1.getDescription(), added_transaction.getDescription());
    }
    @Test
    @DisplayName("получение транзакции пол Id пользователя")
    void getTransactionsByUserId_returnListOfTransactions() {
        repository.addTransaction(transaction1);
        repository.addTransaction(transaction2);

        List<Transaction> full_list = repository.getTransactionsByUserID(UserIdOwner.getInstance().getUserID());
        List<Transaction> empty_list = repository.getTransactionsByUserID(UUID.randomUUID());

        Assertions.assertTrue(empty_list.isEmpty());
        Assertions.assertTrue(full_list.contains(transaction1)&&full_list.contains(transaction2));
    }
    @Test
    @DisplayName("получение транзакции по id транзакции")
    void getTransactionById_returnOptional() {
        repository.addTransaction(transaction1);
        Optional<Transaction> getted_tr = repository.getTransactionByTransactionID(transaction1.getTransactionID());
        Optional<Transaction> empty_tr = repository.getTransactionByTransactionID(UUID.randomUUID());
        Assertions.assertTrue(getted_tr.isPresent());
        Assertions.assertEquals(getted_tr.get(),transaction1);
        Assertions.assertTrue(empty_tr.isEmpty());
    }
    @Test
    @DisplayName("успешное/ошиьочное удаление транзакции")
    void deleteTransaction_retrnBoolean(){
        repository.addTransaction(transaction1);
        boolean deleted = repository.deleteTransaction(transaction1.getTransactionID());
        boolean fail = repository.deleteTransaction(UUID.randomUUID());
        Assertions.assertTrue(deleted);
        Assertions.assertFalse(fail);
    }
    @Test
    @DisplayName("обновление транзакции")
    void updateTransaction_returnOptional() {
        repository.addTransaction(transaction1);
        Optional<Transaction> updated_tr = repository.updateTransaction(transaction2, transaction1.getTransactionID());
        Optional<Transaction> empty_tr = repository.updateTransaction(transaction2, UUID.randomUUID());
        Assertions.assertTrue(empty_tr.isEmpty());
        Assertions.assertTrue(updated_tr.isPresent());
        Assertions.assertEquals(updated_tr.get().getTransactionID(),transaction1.getTransactionID());
        Assertions.assertEquals(updated_tr.get().getCategory(),transaction2.getCategory());
    }
}
