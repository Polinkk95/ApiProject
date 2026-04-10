package org.example.exception;

public class HorseNotFoundException extends RuntimeException{
    public HorseNotFoundException(String message) {
        super(message);
    }
}
