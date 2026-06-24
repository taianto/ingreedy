package com.ingreedy.core.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User with id '%d' was not found.".formatted(id));
    }
}
