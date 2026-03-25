package dev.estv.pet_crud_api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AddressRecordDTO(

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "State is required")
        String state
) {
}
