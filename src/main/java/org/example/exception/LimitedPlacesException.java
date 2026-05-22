package org.example.exception;

public class LimitedPlacesException extends RuntimeException {
    public LimitedPlacesException(String message) {
        super(message);
    }
}
