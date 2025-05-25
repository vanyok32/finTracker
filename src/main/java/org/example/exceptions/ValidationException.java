package org.example.exceptions;

import lombok.Getter;
import org.example.validators.Error;

import java.util.List;

public class ValidationException extends RuntimeException {
    @Getter
    private final List<Error> errors;
    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }
}
