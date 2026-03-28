package dev.estv.pet_crud_api.exception;

public class InvalidAgeException extends RuntimeException {
    public InvalidAgeException() {
        super("Age must be between 1 and 30");
    }

    public InvalidAgeException(String message) {
        super(message);
    }
}
