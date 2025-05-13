package ServiceTest;

import org.example.dto.TransactionRequestDTO;
import org.example.dto.TransactionResponseDTO;
import org.example.enums.TransactionCategory;
import org.example.enums.TransactionType;
import org.example.mappers.TransactionMapper;
import org.example.model.Transaction;
import org.example.repositories.interfaces.TransactionRepository;
import org.example.service.imlementations.TransactionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {


    private Transaction transaction;
    private Transaction transaction2; //for GetByUserId (list of transactions)
    private TransactionResponseDTO transactionResponseDTO;
    private TransactionResponseDTO transactionResponseDTO2; //for GetByUserId (list of transactions)
    private TransactionRequestDTO transactionRequestDTO;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionMapper transactionMapper;
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        transaction = new Transaction(TransactionType.MINUS, 2800.00, TransactionCategory.SPORT,
                "купил абонемент в ddx");
        transaction.setTransactionID(UUID.randomUUID());
        transaction.setUserID(UUID.randomUUID());
        transaction2 = new Transaction(TransactionType.PLUS, 333.00, TransactionCategory.CASH,
                "дали денек");
        transaction2.setTransactionID(UUID.randomUUID());
        transaction2.setUserID(transaction.getUserID());
        transactionResponseDTO = new TransactionResponseDTO(transaction.getAmount(), transaction.getCategory(),
                transaction.getDate(), transaction.getType(), transaction.getDescription(), transaction.getTransactionID(),
                transaction.getUserID());
        transactionResponseDTO2 = new TransactionResponseDTO(transaction2.getAmount(), transaction2.getCategory(),
                transaction2.getDate(), transaction2.getType(), transaction2.getDescription(), transaction2.getTransactionID(),
                transaction2.getUserID());
        transactionRequestDTO = TransactionRequestDTO.builder().amount(2800.00).type(TransactionType.MINUS)
                .date(LocalDateTime.now()).description("купил абонемент в ddx").build();
    }

    @Test
    @DisplayName("Успешно добавляет транзакцию")
    void addTransaction_ValidTransaction_addsTransaction() {
        /**
         * Тестовый метод состоит из 3 частей - AAA
         * Arrange - присвоение поведения и создание необходимых объектов
         * Act - действие, которое будем проверять
         * Assert - проверка результата теста
         **/
        //arrange
        when(transactionRepository.addTransaction(transaction)).thenReturn(transaction);
        when(transactionMapper.toResponseDTO(transaction)).thenReturn(transactionResponseDTO);
        when(transactionMapper.toTransaction(transactionRequestDTO)).thenReturn(transaction);
        //act
        TransactionResponseDTO actual_responceDTO = transactionService.addTransaction(transactionRequestDTO);
        //assert
        Assertions.assertNotNull(actual_responceDTO);
        Assertions.assertEquals(transactionResponseDTO.getAmount(), actual_responceDTO.getAmount());
        Assertions.assertEquals(transactionResponseDTO.getCategory(), actual_responceDTO.getCategory());
        Mockito.verify(transactionRepository, Mockito.times(1)).addTransaction(transaction);
    }

    @Test
    @DisplayName("Успешно обновляет транзакцию")
    void updateTransaction_ValidTransaction_updatesTransaction() {
        //arrange
        when(transactionRepository.updateTransaction(transaction, transaction.getTransactionID()))
                .thenReturn(true);
        when(transactionMapper.toResponseDTO(transaction)).thenReturn(transactionResponseDTO);
        when(transactionMapper.toTransaction(transactionRequestDTO)).thenReturn(transaction);
        //act
        TransactionResponseDTO updated_transactionDTO = transactionService
                .updateTransaction(transactionRequestDTO, transaction.getTransactionID());
        //assert
        Assertions.assertNotNull(updated_transactionDTO);
        Assertions.assertEquals(transactionResponseDTO.getAmount(), updated_transactionDTO.getAmount());
        Assertions.assertEquals(transactionResponseDTO.getCategory(), updated_transactionDTO.getCategory());
        Mockito.verify(transactionRepository, Mockito.times(1)).updateTransaction(transaction, transaction.getTransactionID());
    }

    @Test
    @DisplayName("Успешное удаление транзакции")
    void deleteTransaction_ValidTransaction_deletesTransaction() {
        //arrange
        when(transactionRepository.deleteTransaction(transaction.getTransactionID())).thenReturn(true);
        //act
        boolean flag = transactionService.deleteTransaction(transaction.getTransactionID());
        //assert
        Assertions.assertTrue(flag);
        Mockito.verify(transactionRepository, Mockito.times(1)).deleteTransaction(transaction.getTransactionID());
    }

    @Test
    @DisplayName("Успешное получение по Id транзакции")
    void getByTransactionID_ValidTransaction_returnsTransaction() {
        //arrange
        when(transactionRepository.getTransactionByTransactionID(transaction.getTransactionID()))
                .thenReturn(Optional.of(transaction));
        when(transactionMapper.toResponseDTO(transaction)).thenReturn(transactionResponseDTO);
        //act
        TransactionResponseDTO returned_dto = transactionService.getTransactionByID(transaction.getTransactionID());
        //assert
        Assertions.assertNotNull(returned_dto);
        Assertions.assertEquals(transactionResponseDTO.getAmount(), returned_dto.getAmount());
        Assertions.assertEquals(transactionResponseDTO.getCategory(), returned_dto.getCategory());
        Mockito.verify(transactionRepository, Mockito.times(1)).getTransactionByTransactionID(transaction.getTransactionID());
    }

    @Test
    @DisplayName("успешное получение по id пользователя")
    void getByUserID_ValidTransaction_returnsTransaction() {
        //arrange
        when(transactionRepository.getTransactionsByUserID(transaction.getUserID())).thenReturn(List.of(transaction,transaction2));
        when(transactionMapper.toResponseDTO(transaction)).thenReturn(transactionResponseDTO);
        when(transactionMapper.toResponseDTO(transaction2)).thenReturn(transactionResponseDTO2);
        //act
        List<TransactionResponseDTO> dto_list = transactionService.getTransactionsByUserId(transaction.getUserID());
        //assert
        Assertions.assertNotNull(dto_list);
        Assertions.assertEquals(transactionResponseDTO.getAmount(), dto_list.get(0).getAmount());
        Assertions.assertEquals(transactionResponseDTO.getCategory(), dto_list.get(0).getCategory());
        Assertions.assertEquals(transactionResponseDTO2.getAmount(), dto_list.get(1).getAmount());
        Assertions.assertEquals(transactionResponseDTO2.getCategory(), dto_list.get(1).getCategory());
        Assertions.assertEquals(transactionResponseDTO2.getDescription(), dto_list.get(1).getDescription());
        Mockito.verify(transactionRepository, Mockito.times(1)).getTransactionsByUserID(transaction.getUserID());
    }




}
