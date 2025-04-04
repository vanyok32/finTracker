package org.example.exeptions;

public class UncorrectCommandException extends RuntimeException {
    public UncorrectCommandException(String message) {
        super(message);
    }
}
