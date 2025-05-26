package org.example.repositories.implementations;
import org.example.enums.UserRole;
import org.example.enums.UserStatus;
import org.example.exceptions.TransactionRepositoryException;
import org.example.exceptions.UserRepositoryException;
import org.example.model.User;
import org.example.repositories.interfaces.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.example.utils.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserRepositoryImpl implements UserRepository {

    private final static UserRepositoryImpl INSTANCE = new UserRepositoryImpl();
    private final static String ADD_SQL = """
            INSERT INTO users 
            (name, email, password) 
            VALUES (?,?,?)
              """;
    private final static String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?""";
    private final static String GET_BY_ID_SQL = """
            SELECT * FROM users
            WHERE id = ?""";
    private final static String GET_BY_EMAIL_SQL = """
            SELECT * FROM users
            WHERE email = ?""";
    private final static String GET_ALL_SQL = """
            SELECT * FROM users""";
    private final static String UPDATE_SQL = """
            UPDATE users 
            SET name = ?, email = ?, password = ?
            WHERE id = ?""";
    private final static String BLOCK_SQL = """
            UPDATE users
            set status = 'blocked'
            where email = ?""";
    private final static String UNBLOCK_SQL = """
            UPDATE users
            set status = 'active'
            where email = ?""";
    private static final String GET_STATUS_SQL = """
            SELECT status FROM users
            where email = ?""";
    private static final String GET_ROLE_SQL = """
            SELECT user_role FROM users
            where email = ?""";



    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Override
    public User addUser(User user) {
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(ADD_SQL, Statement.RETURN_GENERATED_KEYS)){
                statement.setString(1, user.getName());
                statement.setString(2,user.getEmail());
                statement.setString(3, user.getPassword());
                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setUserID(UUID.fromString(generatedKeys.getString(1)));
                }
                return user;
        } catch (SQLException e){
                logger.error(e.getMessage());
                throw new UserRepositoryException(e);
        }
    }

    @Override
    public boolean updateUser(User user, UUID id) {
        logger.debug("попытка обновить данные пользователя");
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setObject(4, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new UserRepositoryException(e);
        }
    }

    @Override
    public boolean removeUser(UUID id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setObject(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new UserRepositoryException(e);
        }
    }

    @Override
    public Optional<User> getUserByID(UUID id) {
        try(var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(GET_BY_ID_SQL)) {
            statement.setObject(1, id);
            var resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                 user = buildUser(resultSet);
            }
            return Optional.ofNullable(user);

        } catch (SQLException e) {
            throw new UserRepositoryException(e);
        }
    }



    @Override
    public Optional<User> getUserByEmail(String email) {
        try(var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(GET_BY_EMAIL_SQL)) {
            statement.setString(1, email);
            var resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = buildUser(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new UserRepositoryException(e);
        }
    }

    @Override
    public List<User> getUsers() {
        logger.info("возвращается список всех пользователей");
        try(var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(GET_ALL_SQL)){
            List<User> users = new ArrayList<>();
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new UserRepositoryException(e);
        }
    }

    @Override
    public boolean blockUser(String email){
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(BLOCK_SQL)) {
            statement.setString(1,email);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new UserRepositoryException(e);
        }
    }
    @Override
    public boolean unBlockUser(String email){
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(UNBLOCK_SQL)) {
            statement.setString(1,email);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new TransactionRepositoryException(e);
        }
    }
    @Override
    public UserStatus getUserStatus(String email) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(GET_STATUS_SQL)) {
            statement.setString(1, email);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return UserStatus.valueOf(resultSet.getString("status").toUpperCase());
            }
            return null;
        } catch (SQLException e) {
            throw new UserRepositoryException (e);
        }
    }
    @Override
    public UserRole getUserRole(String email) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(GET_ROLE_SQL)) {
            statement.setString(1, email);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return UserRole.valueOf(resultSet.getString("user_role").toUpperCase());
            }
            return null;
        } catch (SQLException e) {
            throw new UserRepositoryException (e);
        }
    }

    private static User buildUser(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                UUID.fromString(resultSet.getString("id")));
    }
    public static UserRepositoryImpl getInstance() {
        return INSTANCE;
    }
    private UserRepositoryImpl() {}
}
