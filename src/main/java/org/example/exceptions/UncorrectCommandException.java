package org.example.exceptions;

public class UncorrectCommandException extends RuntimeException {
    public UncorrectCommandException(String message) {
        super(message);
    }
}
