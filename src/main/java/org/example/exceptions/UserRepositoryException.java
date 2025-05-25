package org.example.exceptions;

import java.sql.SQLException;

public class UserRepositoryException extends RuntimeException {
    public UserRepositoryException(SQLException message) {
        super(message);
    }
}
