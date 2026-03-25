package dev.estv.pet_crud_api.util;

import dev.estv.pet_crud_api.dto.request.AddressRecordDTO;
import dev.estv.pet_crud_api.dto.request.PetRecordDTO;
import dev.estv.pet_crud_api.exception.*;
import dev.estv.pet_crud_api.model.PetAddressModel;
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
                .address(buildAddress(dto.address()))
                .age(normalizeField(dto.age().toLowerCase()))
                .weight(normalizeField(dto.weight().toLowerCase()))
                .race(normalizeField(dto.race().toLowerCase()))
                .build();
    }

    public PetAddressModel buildAddress(AddressRecordDTO dto) {
        PetAddressModel address = new PetAddressModel();

        address.setCity(dto.city());
        address.setState(dto.state());

        return address;
    }

    public void normalizeAddress(PetModel petModel) {

        if (petModel.getAddress() == null) throw new InvalidAddressException();

        PetAddressModel address = petModel.getAddress();

        address.setCity(normalizeField(address.getCity()));
        address.setState(normalizeField(address.getState()));
    }

    public String normalizeField(String value) {
        return (value == null || value.isBlank()) ? NA : value;
    }

    public void validatePet(PetModel petModel) {
        if (!petModel.getName().matches("^[A-Za-zÀ-ÿ]+(?:\\s+[A-Za-zÀ-ÿ]+)+$")) {
            throw new InvalidNameException();
        }

        if (petModel.getType() == null) throw new InvalidTypeException();
        if (petModel.getGender() == null) throw new InvalidGenderException();

        if (petModel.getAddress().getCity().length() > 30
                || petModel.getAddress().getState().length() > 30) {
            throw new InvalidAddressException();
        }

        if (petModel.getAge().matches("^\\d+(\\.\\d+)?$")) {
            if (Double.parseDouble(petModel.getAge()) < 0.1
                    || Double.parseDouble(petModel.getAge()) > 130) {
                throw new InvalidAgeException();
            }
        }

        if (petModel.getWeight().matches("^\\d+(\\.\\d+)?$")) {
            if (Double.parseDouble(petModel.getWeight()) < 0.5
                    || Double.parseDouble(petModel.getWeight()) > 90) {
                throw new InvalidWeightException();
            }
        }

        if (petModel.getRace().length() > 15) {
            throw new InvalidRaceException();
        }
    }
}
