package org.example.validators;

import lombok.Value;

@Value(staticConstructor = "of")
public class Error {
    String message;
    String code;
}
