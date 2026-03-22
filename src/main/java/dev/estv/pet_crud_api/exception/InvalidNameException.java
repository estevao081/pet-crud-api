package dev.estv.pet_crud_api.exception;

public class InvalidNameException extends RuntimeException {
    public InvalidNameException() {
        super("Pet should have first and last name");
    }
    public InvalidNameException(String message) {
        super(message);
    }
}
