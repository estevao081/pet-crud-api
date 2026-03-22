package dev.estv.pet_crud_api.exception;

public class InvalidGenderException extends RuntimeException {

    public InvalidGenderException() {
        super("Invalid Gender");
    }

    public InvalidGenderException(String message) {
        super(message);
    }
}
