package dev.tsumakov.appointments.common.exception;

public class StringValidationException extends RuntimeException {
    public StringValidationException(String message) {
        super(message);
    }
}
