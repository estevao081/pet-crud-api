package dev.estv.pet_crud_api.dto.request;

import dev.estv.pet_crud_api.model.PetAddressModel;
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

        PetAddressModel address,

        @Pattern(
                regexp = "^(\\s*|\\d+([.,]\\d+)?|não informado)$",
                message = "Age must be a number or NA"
        )
        String age,

        @Pattern(
                regexp = "^(\\s*|\\d+([.,]\\d+)?|não informado)$",
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
