package dev.estv.pet_crud_api.util;

import dev.estv.pet_crud_api.dto.request.PetRecordDTO;
import dev.estv.pet_crud_api.exception.*;
import dev.estv.pet_crud_api.model.PetModel;
import org.springframework.stereotype.Component;

@Component
public class Util {

    private static final String NA = "não informado";

    public PetModel toEntity(PetRecordDTO dto) {
        return PetModel.builder()
                .name(dto.name().toLowerCase())
                .type(PetModel.Type.fromString(dto.type()))
                .gender(PetModel.Gender.fromString(dto.gender()))
                .city(dto.city())
                .state(dto.state())
                .age(normalizeField(dto.age()))
                .weight(normalizeField(dto.weight()))
                .race(normalizeField(dto.race()))
                .build();
    }

    public String normalizeField(String value) {
        return (value == null || value.isBlank()) ? NA : value.toLowerCase();
    }

    public void validatePet(PetModel petModel) {
        if (!petModel.getName().matches("^[A-Za-zÀ-ÿ]+(?:\\s+[A-Za-zÀ-ÿ]+)+$")
                || petModel.getName().length() > 40) {
            throw new InvalidNameException();
        }

        if (petModel.getType() == null) throw new InvalidTypeException();
        if (petModel.getGender() == null) throw new InvalidGenderException();

        if (petModel.getCity().isBlank()
                || petModel.getCity().length() > 40
                || petModel.getState().isBlank()) {
            throw new InvalidAddressException();
        }

        if (!petModel.getAge().matches("^(?:[1-9]|[1-2]\\d|30|não informado)$")) {
            throw new InvalidAgeException();
        }

        if (!petModel.getWeight().matches("^(?:[1-9]|[1-8]\\d|90|não informado)$")) {
            throw new InvalidWeightException();
        }

        if (petModel.getRace().length() > 20) {
            throw new InvalidRaceException();
        }
    }
}
