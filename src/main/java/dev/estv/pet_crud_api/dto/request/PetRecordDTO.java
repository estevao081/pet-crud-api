package dev.estv.pet_crud_api.dto.request;

import dev.estv.pet_crud_api.model.PetModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PetRecordDTO(
        @NotBlank(message = "Name is required")
        String name,
        @NotNull
        PetModel.Type type,
        @NotNull
        PetModel.Gender gender,
        List<String>address,
        Integer age,
        Integer weight,
        String race
) {
}
