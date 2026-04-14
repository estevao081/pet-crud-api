package dev.estv.pet_crud_api.exception.handler;

import dev.estv.pet_crud_api.dto.response.ApiResponse;
import dev.estv.pet_crud_api.exception.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, null, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(
            MethodArgumentNotValidException ex) {

        var errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, errors, "Validation Error"));
    }

    @ExceptionHandler({
            InvalidTypeException.class,
            InvalidGenderException.class,
            InvalidNameException.class,
            InvalidAddressException.class,
            InvalidAgeException.class,
            InvalidWeightException.class,
            InvalidRaceException.class,
            InvalidEmailException.class,
            InvalidPasswordException.class,
            InvalidNumberException.class,})
    public ResponseEntity<ApiResponse<Void>> handleBusiness(RuntimeException ex) {
        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, null, ex.getMessage()));
    }
}
