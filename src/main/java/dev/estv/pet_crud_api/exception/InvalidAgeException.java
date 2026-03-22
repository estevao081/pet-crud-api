package dev.estv.pet_crud_api.exception;

public class InvalidAgeException extends RuntimeException {
    public InvalidAgeException() {
        super("Age must be between 0.1 and 20");
    }

    public InvalidAgeException(String message) {
        super(message);
    }
}
