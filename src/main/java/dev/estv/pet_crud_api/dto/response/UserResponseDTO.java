package dev.estv.pet_crud_api.dto.response;

import dev.estv.pet_crud_api.model.UserModel;

public record UserResponseDTO (
        String id,
        String name,
        String number,
        String email,
        String role
){}
