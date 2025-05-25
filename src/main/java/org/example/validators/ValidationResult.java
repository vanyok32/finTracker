package org.example.validators;

import lombok.Getter;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;


public class ValidationResult {
    @Getter
    private final List<Error> errors = new ArrayList<>();

    public void addError(Error error) {
        errors.add(error);
    }
    public boolean isValid(){
        return errors.isEmpty();
    }
}
