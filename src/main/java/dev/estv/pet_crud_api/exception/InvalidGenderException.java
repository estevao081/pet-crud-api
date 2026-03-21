package dev.estv.pet_crud_api.exception;

public class InvalidGenderException extends RuntimeException {

    public InvalidGenderException() {
        super("ERRO: Gênero inválido");
    }

    public InvalidGenderException(String message) {
        super(message);
    }
}
