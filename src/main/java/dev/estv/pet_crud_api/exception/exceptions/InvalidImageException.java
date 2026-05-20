package dev.estv.pet_crud_api.exception.exceptions;

public class InvalidImageException extends RuntimeException{

    public InvalidImageException() {super("Select an Image with 5MB or less");}

    public InvalidImageException(String message) {super(message);}
}
