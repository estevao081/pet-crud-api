package dev.estv.pet_crud_api.util;

import dev.estv.pet_crud_api.dto.response.PetResponseDTO;
import dev.estv.pet_crud_api.model.PetModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PetMapper {

    @Mapping(target = "id", expression = "java(pet.getId().toString())")
    @Mapping(target = "type", expression = "java(pet.getType().name())")
    @Mapping(target = "gender", expression = "java(pet.getGender().name())")
    @Mapping(target = "owner", source = "owner")
    PetResponseDTO toDTO(PetModel pet);
}
