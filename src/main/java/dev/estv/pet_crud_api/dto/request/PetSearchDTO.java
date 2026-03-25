package dev.estv.pet_crud_api.dto.request;

public record PetSearchDTO(
        String name,
        String type,
        String gender,
        AddressRecordDTO dtoAddress,
        String age,
        String weight,
        String race
) {
}