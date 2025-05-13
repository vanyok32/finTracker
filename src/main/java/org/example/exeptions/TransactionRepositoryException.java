package org.example.exeptions;

import java.sql.SQLException;

public class TransactionRepositoryException extends RuntimeException {
  public TransactionRepositoryException(SQLException   message) {
    super(message);
  }
}
