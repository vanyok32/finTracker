package org.example.validators;

public interface Validator<T> {
    ValidationResult isValid(T object);
}
