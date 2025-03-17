package org.example.serviceforcouriers.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Пользователь не был найден");
    }
}
