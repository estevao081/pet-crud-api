package dev.estv.pet_crud_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PetRecordDTO(
        @NotBlank(message = "Name is required")
        String name,

        @NotNull
        String type,

        @NotNull
        String gender,

        @Pattern(
                regexp = "^[A-Za-zÀ-ÿ\\s]*$",
                message = "Street must have letters only"
        )
        String street,

        @Pattern(
                regexp = "^(\\s*|\\d+([.,]\\d+)?|NÃO INFORMADO)$",
                message = "Number must have numbers only"
        )
        String number,

        @Pattern(
                regexp = "^[A-Za-zÀ-ÿ\\s]*$",
                message = "City must have letters only"
        )
        String city,

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
