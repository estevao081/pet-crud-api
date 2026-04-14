package dev.estv.pet_crud_api.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super("Invalid email");
    }
    public InvalidEmailException(String message) {
        super(message);
    }
}
