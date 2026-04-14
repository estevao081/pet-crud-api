package dev.estv.pet_crud_api.dto.request;

import jakarta.validation.constraints.*;

public record UserRecordDTO(
        @NotNull
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotBlank(message= "Phone number cannot be blank")
        @NotNull
        String number,
        @NotNull
        @NotBlank(message = "E-mail cannot be blank")
        String email,
        @NotNull
        @NotBlank(message = "Password cannot be blank")
        String password
){}
