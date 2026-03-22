package dev.estv.pet_crud_api.service;

import dev.estv.pet_crud_api.dto.request.PetRecordDTO;
import dev.estv.pet_crud_api.exception.*;
import dev.estv.pet_crud_api.model.PetModel;
import dev.estv.pet_crud_api.repository.PetRepository;
import dev.estv.pet_crud_api.specification.PetSpecification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class PetService {

    private final PetRepository petRepository;
    private static final String NA = "NÃO INFORMADO";

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public PetModel save(PetRecordDTO petRecordDTO) {
        PetModel petModel = toEntity(petRecordDTO);
        normalizeAddress(petModel);
        validatePet(petModel);
        return petRepository.save(petModel);
    }

    public List<PetModel> findAll() {
        return petRepository.findAll();
    }

    public boolean delete(UUID id) {
        if (!petRepository.existsById(id)) {
            return false;
        }

        petRepository.deleteById(id);
        return true;
    }

    public List<PetModel> search(PetRecordDTO dto) {
        PetModel.Type type = null;

        if (dto.type() != null) {
            type = mapType(dto.type());
        }

        return petRepository.findAll(PetSpecification.filter(dto));
    }

    private static PetModel.Type mapType(String value) {
        return switch (value.toLowerCase()) {
            case "cao" -> PetModel.Type.CAO;
            case "gato" -> PetModel.Type.GATO;
            default -> throw new InvalidTypeException();
        };
    }

    private static PetModel.Gender mapGender(String value) {
        return switch (value.toLowerCase()) {
            case "f" -> PetModel.Gender.F;
            case "m" -> PetModel.Gender.M;
            default -> throw new InvalidGenderException();
        };
    }

    private PetModel toEntity(PetRecordDTO dto) {
        return PetModel.builder()
                .name(dto.name())
                .type(mapType(dto.type()))
                .gender(mapGender(dto.gender()))
                .address(dto.address())
                .age(dto.age())
                .weight(dto.weight())
                .race(dto.race())
                .build();
    }

    private static void validatePet(PetModel petModel) {
        if (!petModel.getName().matches("^[A-Za-zÀ-ÿ]+(?:\\s+[A-Za-zÀ-ÿ]+)+$")) {
            throw new InvalidNameException();
        }

        if (petModel.getAddress().size() != 3) {
            throw new InvalidAddressException();
        }

        if (petModel.getAge().matches("^\\d+(\\.\\d+)?$")) {
            if (Double.parseDouble(petModel.getAge()) < 0.1
                    || Double.parseDouble(petModel.getAge()) > 20) {
                throw new InvalidAgeException();
            }
        } else {
            petModel.setAge(NA);
        }

        if (petModel.getWeight().matches("^\\d+(\\.\\d+)?$")) {
            if (Double.parseDouble(petModel.getWeight()) < 0.5
                    || Double.parseDouble(petModel.getWeight()) > 60) {
                throw new InvalidWeightException();
            }
        } else {
            petModel.setWeight(NA);
        }

        if(petModel.getRace().length() > 15) {
            throw new InvalidRaceException();
        }

        if(petModel.getRace().isBlank()) {
            petModel.setRace(NA);
        }
    }

    private void normalizeAddress(PetModel petModel) {
        if (petModel.getAddress() == null) return;

        List<String> normalized = new ArrayList<>();

        for (String field : petModel.getAddress()) {
            if (field == null || field.isBlank()) {
                normalized.add(NA);
            } else {
                normalized.add(field);
            }
        }

        petModel.setAddress(normalized);
    }
}
