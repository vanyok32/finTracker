package org.example.service.imlementations;

import org.example.dto.TransactionRequestDTO;
import org.example.dto.TransactionResponseDTO;
import org.example.mappers.TransactionMapper;
import org.example.mappers.UserMapper;
import org.example.model.Transaction;
import org.example.repositories.implementations.TransactionRepositoryImpl;
import org.example.service.interfaces.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TransactionServiceImpl implements TransactionService {
    TransactionRepositoryImpl transactionRepository;
    TransactionMapper transactionMapper = new TransactionMapper();

    Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Override
    public TransactionResponseDTO addTransaction(TransactionRequestDTO transactionRequestDTO) {
        logger.debug("Добавление в TrService");
        Transaction transaction = transactionRepository.addTransaction
                (transactionMapper.toTransaction(transactionRequestDTO));
        return transactionMapper.toResponseDTO(transaction);

    }

    @Override
    public void deleteTransaction(UUID id) {
        logger.debug("Удаление в TrService");
        transactionRepository.deleteTransaction(id);
    }

    @Override
    public TransactionResponseDTO updateTransaction(TransactionRequestDTO transactionRequestDTO, UUID id) {
        logger.debug("Обновление в TrService");
        Transaction tr = transactionMapper.toTransaction(transactionRequestDTO);
        tr.setTransactionID(id);
        transactionRepository.updateTransaction(tr, id);
        return transactionMapper.toResponseDTO(tr);
    }

    @Override
    public TransactionResponseDTO getTransactionByID(UUID id) {
        logger.debug("получение по id в TrService");
        Optional<Transaction> op = transactionRepository.getTransactionByTransactionID(id);
        if (op.isPresent()) return transactionMapper.toResponseDTO(op.get());
        else return null;//todo продумать возврат
    }

    @Override
    public List<TransactionResponseDTO> getTransactionsByUserId(UUID userId) {
        logger.debug("получение по UserID в TrService");
        List<Transaction> tr = transactionRepository.getTransactionsByUserID(userId);
        List<TransactionResponseDTO> trDTO = new ArrayList<TransactionResponseDTO>();
        for (Transaction transaction : tr) {
            trDTO.add(transactionMapper.toResponseDTO(transaction));
        }
        return trDTO;
    }
}
