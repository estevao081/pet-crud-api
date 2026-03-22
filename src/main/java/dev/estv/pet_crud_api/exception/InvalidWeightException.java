package dev.estv.pet_crud_api.exception;

public class InvalidWeightException extends RuntimeException {
    public InvalidWeightException() {
        super("Weight must be between 0.5 and 60");
    }

    public InvalidWeightException(String message) {
        super(message);
    }
}
