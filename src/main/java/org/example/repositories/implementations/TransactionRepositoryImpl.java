package org.example.repositories.implementations;

import org.example.entrypoint.UserIdOwner;
import org.example.enums.TransactionCategory;
import org.example.enums.TransactionType;
import org.example.exeptions.TransactionRepositoryException;
import org.example.model.Transaction;
import org.example.repositories.interfaces.TransactionRepository;
import org.example.utils.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



public class TransactionRepositoryImpl implements TransactionRepository {

    private Logger logger = LoggerFactory.getLogger(TransactionRepositoryImpl.class);
    private static final TransactionRepositoryImpl INSTANCE = new TransactionRepositoryImpl();

    private final static String ADD_SQL = """
            INSERT INTO transactions
            (user_id, amount, type, category, description, created_at)
            VALUES (?, ?, ?, ?, ?,?)""";
    private static final String GET_BY_ID_SQL = """
            SELECT id, user_id, amount, type, description, created_at, category
            FROM transactions
            WHERE user_id = ?""";
    private static final String GET_BY_ID = """
            SELECT id, user_id, amount, type, description, created_at, category
            FROM transactions
            WHERE id = ?""";
    private static final String DELETE_SQL = """
            DELETE FROM transactions
            WHERE id = ?""";
    private static final String UPDATE_SQL = """
            UPDATE transactions
            SET amount = ?, type = ?, category = ?, description = ?, created_at = ?
            where id = ?""";


    @Override
    public Transaction addTransaction(Transaction transaction){
        logger.debug("попытка добавления транзакции");
        try(var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(ADD_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setObject(1,UserIdOwner.getInstance().getUserID());
            statement.setDouble(2,transaction.getAmount());
            statement.setString(3,transaction.getType().toString());
            statement.setString(4,transaction.getCategory().toString());
            statement.setString(5,transaction.getDescription());
            statement.setTimestamp(6, Timestamp.valueOf(transaction.getDate()));
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                transaction.setTransactionID(UUID.fromString(generatedKeys.getString(1)));
                logger.info("транзакция добавлена, id: {}", transaction.getTransactionID());
            }
            return transaction;
        } catch (SQLException e) {
            throw new TransactionRepositoryException(e);
        }

    }

    @Override
    public List<Transaction> getTransactionsByUserID (UUID UserID){
        logger.debug("попытка получения транзакции по userID");
        try(var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(GET_BY_ID_SQL);) {
            statement.setObject(1,UserIdOwner.getInstance().getUserID());
            var resultSet = statement.executeQuery();
            List<Transaction> transactions = new ArrayList<>();
            while (resultSet.next()) {
                transactions.add(build(resultSet));
            }
            logger.info("возвращено транзакций: {}", transactions.size());
            return transactions;
        } catch (SQLException e) {
            throw new TransactionRepositoryException(e);
        }
    }
    @Override
    public Optional<Transaction> getTransactionByTransactionID (UUID TransactionID){
        logger.debug("попытка получения транзакции по transactionID");
        try(var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setObject(1,TransactionID);
            var resultSet = statement.executeQuery();
            Transaction transaction = null;
            while (resultSet.next()) {
                transaction = build(resultSet);
            }
            return Optional.ofNullable(transaction);
        } catch (SQLException e) {
            throw new TransactionRepositoryException(e);
        }
    }
    @Override
    public boolean deleteTransaction(UUID id){
        logger.debug("попытка удаления транзакции");
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setObject(1,id);
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            throw new TransactionRepositoryException(e);
        }

    }
    @Override
    public boolean updateTransaction(Transaction transaction, UUID id){
        logger.debug("попытка обновления транзакции");
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setDouble(1, transaction.getAmount());
            statement.setString(2, transaction.getType().toString());
            statement.setString(3, transaction.getCategory().toString());
            statement.setString(4, transaction.getDescription());
            statement.setTimestamp(5, Timestamp.valueOf(transaction.getDate()));
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new TransactionRepositoryException(e);
        }
    }



    private static Transaction build(ResultSet rs) throws SQLException {
        return new Transaction(TransactionType.valueOf(rs.getString("type")),
                TransactionCategory.valueOf(rs.getString("category")),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getString("description"),
                rs.getDouble("amount"),
                UUID.fromString(rs.getString("user_id")), UUID.fromString(rs.getString("id")));
    }
    public static TransactionRepositoryImpl getInstance() {
        return INSTANCE;
    }
    private TransactionRepositoryImpl() {}

}
