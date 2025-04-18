package ServiceTest;

import org.example.dto.UserRequestDTO;
import org.example.dto.UserResponseDTO;
import org.example.exeptions.UserNotFoundException;
import org.example.mappers.UserMapper;
import org.example.model.User;
import org.example.repositories.interfaces.UserRepository;
import org.example.service.imlementations.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private User user;
    private User user2;
    private UserRequestDTO userRequestDTO;
    private UserRequestDTO userRequestDTO2;
    private UserResponseDTO userResponseDTO;
    private UserResponseDTO userResponseDTO2;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
       user = new User("Тимур Олегович Беженарь", "bezhenar_TO@mail.ru", "radditkafka", UUID.randomUUID());
       user2 = new User("Семен Валерьевич Мерченко", "kubanoid123@gmail.com", "javajava123", UUID.randomUUID());
       userRequestDTO = new UserRequestDTO(user.getName(),  user.getEmail(), user.getPassword());
       userRequestDTO2 = new UserRequestDTO(user2.getName(),  user2.getEmail(), user2.getPassword());
       userResponseDTO = new UserResponseDTO(user.getUserID(), user.getName(), user.getEmail());
       userResponseDTO2 = new UserResponseDTO(user2.getUserID(), user2.getName(), user2.getEmail());

    }
    @Test
    @DisplayName("Успешная регистрация пользователя")
    void registerUser_ValidUser_ReturnUserDTO() {
        //arrange
        when(userRepository.addUser(user)).thenReturn(user);
        when(userMapper.toUser(userRequestDTO)).thenReturn(user);
        when(userMapper.toResponseDTO(user)).thenReturn(userResponseDTO);
        //act
        UserResponseDTO result_userDTO = userService.registerUser(userRequestDTO);
        //assert
        Assertions.assertNotNull(result_userDTO);
        Assertions.assertEquals(userResponseDTO.getEmail(), result_userDTO.getEmail());
        Assertions.assertEquals(userResponseDTO.getName(), result_userDTO.getName());
        Assertions.assertEquals(userResponseDTO.getId(), result_userDTO.getId());
        Mockito.verify(userRepository, Mockito.times(1)).addUser(user);
    }
    @Test
    @DisplayName("успешное логгирование пользователя")
    void loginUser_ValidUser_ReturnUserDTO() {
        when(userRepository.getUsers()).thenReturn(List.of(user, user2));
        when(userMapper.toUser(userRequestDTO)).thenReturn(user);
        when(userMapper.toUser(userRequestDTO2)).thenReturn(user2);

        boolean flag1 = userService.loginUser(userRequestDTO);
        boolean flag2 = userService.loginUser(userRequestDTO2);

        Assertions.assertTrue(flag1);
        Assertions.assertTrue(flag2);
        Mockito.verify(userRepository, Mockito.times(2)).getUsers();
    }

    @Test
    @DisplayName("Успешное удаление пользователя")
    void deleteUser_ValidUser_ReturnUserDTO() {
        when(userRepository.removeUser(user.getUserID())).thenReturn(true);
        boolean flag1 = userService.deleteUser(user.getUserID());
        Assertions.assertTrue(flag1);
    }
    @Test
    @DisplayName("Успешное обновление пользователя")
    void updateUser_ValidUser_ReturnUserDTO() {
        //arrange
        when(userRepository.updateUser(user, user.getUserID())).thenReturn(Optional.of(user));
        when(userMapper.toUser(userRequestDTO)).thenReturn(user);
        when(userMapper.toResponseDTO(user)).thenReturn(userResponseDTO);
        //act
        UserResponseDTO actual_responceDTO = userService.updateUser(user.getUserID(), userRequestDTO);
        //assert
        Assertions.assertNotNull(actual_responceDTO);
        Assertions.assertEquals(userResponseDTO.getEmail(), actual_responceDTO.getEmail());
        Assertions.assertEquals(userResponseDTO.getName(), actual_responceDTO.getName());
    }
    @Test
    @DisplayName("успешный поиск пользователя по Email")
    void getUserByEmail_ValidData_UserResponceDTO(){
        //arrange
        when(userRepository.getUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userMapper.toResponseDTO(user)).thenReturn(userResponseDTO);
        //act
        UserResponseDTO actual_responceDTO = userService.getUserByEmail(user.getEmail());
        //asserts
        Assertions.assertNotNull(actual_responceDTO);
        Assertions.assertEquals(userResponseDTO.getEmail(), actual_responceDTO.getEmail());
        Assertions.assertEquals(userResponseDTO.getName(), actual_responceDTO.getName());
        Mockito.verify(userRepository, Mockito.times(1)).getUserByEmail(user.getEmail());
    }

    @Test
    @DisplayName("ОШИБОЧНЫЙ поиск по Email")
    void getUserByEmail_InvalidData_UserResponceDTO(){
        when(userRepository.getUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail(user.getEmail()));
    }

    @Test
    @DisplayName("успешное получение пользователя по id")
    void getUserByID_ValidData_UserResponceDTO(){
        when(userRepository.getUserByID(user.getUserID())).thenReturn(Optional.of(user));
        when(userMapper.toResponseDTO(user)).thenReturn(userResponseDTO);

        UserResponseDTO actual_responceDTO = userService.getUserById(user.getUserID());

        Assertions.assertNotNull(actual_responceDTO);
        Assertions.assertEquals(userResponseDTO.getEmail(), actual_responceDTO.getEmail());
        Assertions.assertEquals(userResponseDTO.getName(), actual_responceDTO.getName());
    }
    @Test
    @DisplayName("ОШИБОЧНОЕ получение пользователя по ID")
    void getUserByID_InvalidData_UserResponceDTO(){
        when(userRepository.getUserByID(user.getUserID())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserById(user.getUserID()));
    }

}
