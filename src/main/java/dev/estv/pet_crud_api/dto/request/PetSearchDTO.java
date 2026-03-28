package dev.estv.pet_crud_api.dto.request;

public record PetSearchDTO(
        String name,
        String type,
        String gender,
        String city,
        String state,
        String age,
        String weight,
        String race
) {
}