package dev.estv.pet_crud_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PetRecordDTO(
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Type is required")
        String type,

        @NotNull(message = "Gender is required")
        String gender,

        @NotNull(message = "City is required")
        String city,

        @NotNull(message = "City is required")
        String state,

        String age,
        String weight,

        @Pattern(
                regexp = "^[A-Za-zÀ-ÿ\\s]*$",
                message = "Race must have letters only"
        )
        String race
) {
}
