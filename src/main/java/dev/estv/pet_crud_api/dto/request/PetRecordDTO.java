package dev.estv.pet_crud_api.dto.request;

import jakarta.validation.constraints.*;

import java.util.List;

public record PetRecordDTO(
        @NotBlank(message = "Name is required")
        String name,

        @NotNull
        String type,

        @NotNull
        String gender,

        List<String>address,

        @Pattern(
                regexp = "^(\\s*|\\d+([.,]\\d+)?|NÃO INFORMADO)$",
                message = "Age must be a number or NA"
        )
        String age,

        @Pattern(
                regexp = "^(\\s*|\\d+([.,]\\d+)?|NÃO INFORMADO)$",
                message = "Weight must be a number or NA"
        )
        String weight,

        @Pattern(
                regexp = "^[A-Za-zÀ-ÿ\\s]*$",
                message = "Race must have letters only"
        )
        String race
) {
}
