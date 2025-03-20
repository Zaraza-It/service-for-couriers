package org.example.serviceforcouriers.exceptions;

public class RequestNotFoundException extends RuntimeException {
    public RequestNotFoundException() {
        super("Запрос не был найден.");
    }
}
