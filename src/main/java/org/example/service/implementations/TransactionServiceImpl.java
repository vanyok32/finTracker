package org.example.service.implementations;

import lombok.RequiredArgsConstructor;
import org.example.dto.TransactionRequestDTO;
import org.example.dto.TransactionResponseDTO;
import org.example.exceptions.TransactionNotFoundException;
import org.example.mappers.TransactionMapper;
import org.example.model.Transaction;
import org.example.repositories.implementations.TransactionRepositoryImpl;
import org.example.repositories.interfaces.TransactionRepository;
import org.example.service.interfaces.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TransactionServiceImpl implements TransactionService {
    private final static TransactionRepository transactionRepository = TransactionRepositoryImpl.getInstance();
    private final static TransactionMapper transactionMapper = TransactionMapper.getInstance();
    private final static TransactionServiceImpl INSTANCE = new TransactionServiceImpl();
    private final static Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Override
    public TransactionResponseDTO addTransaction(TransactionRequestDTO transactionRequestDTO, UUID userID) {
        logger.debug("Добавление в TrService");
        Transaction transaction = transactionRepository.addTransaction
                (transactionMapper.toTransaction(transactionRequestDTO), userID);
        return transactionMapper.toResponseDTO(transaction);

    }

    @Override
    public boolean deleteTransaction(UUID id) {
        logger.debug("Удаление в TrService");
        if (!transactionRepository.deleteTransaction(id)) throw new TransactionNotFoundException(id.toString());
        return true;
    }

    @Override
    public TransactionResponseDTO updateTransaction(TransactionRequestDTO transactionRequestDTO, UUID id) {
        logger.debug("Обновление в TrService");
        Transaction tr = transactionMapper.toTransaction(transactionRequestDTO);
        tr.setTransactionID(id);
        if (!transactionRepository.updateTransaction(tr, id)) throw new TransactionNotFoundException(id.toString());
        return transactionMapper.toResponseDTO(tr);
    }

    @Override
    public TransactionResponseDTO getTransactionByID(UUID id) {
        logger.debug("получение по id в TrService");
        return transactionMapper.toResponseDTO(transactionRepository.getTransactionByTransactionID(id).orElseThrow(()
                -> new TransactionNotFoundException(id.toString())));
    }

    @Override
    public List<TransactionResponseDTO> getTransactionsByUserId(UUID userId) {
        logger.debug("получение по UserID в TrService");
        List<Transaction> tr = transactionRepository.getTransactionsByUserID(userId);
        List<TransactionResponseDTO> trDTO = new ArrayList<TransactionResponseDTO>();
        for (Transaction transaction : tr) {
            trDTO.add(transactionMapper.toResponseDTO(transaction));
        }
        if (trDTO.isEmpty()) throw new TransactionNotFoundException(userId.toString());
        return trDTO;
    }
    public static TransactionServiceImpl getInstance() {
        return INSTANCE;
    }
    private TransactionServiceImpl() {}
}
