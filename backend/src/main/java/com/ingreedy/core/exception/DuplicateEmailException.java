package com.ingreedy.core.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("A user with email '%s' already exists.".formatted(email));
    }
}
