package dev.estv.pet_crud_api.dto.response;

public record UserResponseDTO (
        String id,
        String name,
        String number,
        String email
){}
