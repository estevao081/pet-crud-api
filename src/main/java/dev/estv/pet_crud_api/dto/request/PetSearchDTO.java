package dev.estv.pet_crud_api.dto.request;

public record PetSearchDTO(
        String name,
        String type,
        String gender,
        String street,
        String number,
        String city,
        String age,
        String weight,
        String race
) {
}