package dev.estv.pet_crud_api.dto.request;

import jakarta.validation.constraints.*;

import java.util.List;

public record PetSearchDTO(
        String name,
        @NotNull
        String type,
        String gender,
        List<String>address,
        String age,
        String weight,
        String race
) {
}
