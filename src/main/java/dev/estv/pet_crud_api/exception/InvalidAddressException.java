package dev.estv.pet_crud_api.exception;

public class InvalidAddressException extends RuntimeException {
    public InvalidAddressException() {
        super("Addres should contains street, house number and city");
    }
    public InvalidAddressException(String message) {
        super(message);
    }
}
