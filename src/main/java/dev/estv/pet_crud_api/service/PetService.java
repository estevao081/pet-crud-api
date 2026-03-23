package dev.estv.pet_crud_api.service;

import dev.estv.pet_crud_api.dto.request.PetRecordDTO;
import dev.estv.pet_crud_api.dto.request.PetSearchDTO;
import dev.estv.pet_crud_api.exception.*;
import dev.estv.pet_crud_api.model.PetAddressModel;
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
        validatePet(petModel);
        normalizeAddress(petModel);
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

    public List<PetModel> search(PetSearchDTO dto) {
        return petRepository.findAll(PetSpecification.filter(dto));
    }

    private PetModel toEntity(PetRecordDTO dto) {
        return PetModel.builder()
                .name(dto.name())
                .type(PetModel.Type.fromString(dto.type()))
                .gender(PetModel.Gender.fromString(dto.gender()))
                .address(buildAddress(dto))
                .age(normalizeField(dto.age()))
                .weight(normalizeField(dto.weight()))
                .race(normalizeField(dto.race()))
                .build();
    }

    private PetAddressModel buildAddress(PetRecordDTO dto) {
        PetAddressModel address = new PetAddressModel();

        address.setStreet(dto.street());
        address.setNumber(dto.number());
        address.setCity(dto.city());

        return address;
    }

    private void normalizeAddress(PetModel petModel) {

        if (petModel.getAddress() == null) return;

        PetAddressModel address = petModel.getAddress();

        address.setStreet(normalizeField(address.getStreet()));
        address.setNumber(normalizeField(address.getNumber()));
        address.setCity(normalizeField(address.getCity()));
    }

    private String normalizeField(String value) {
        return (value == null || value.isBlank()) ? NA : value;
    }

    private static void validatePet(PetModel petModel) {
        if (!petModel.getName().matches("^[A-Za-zÀ-ÿ]+(?:\\s+[A-Za-zÀ-ÿ]+)+$")) {
            throw new InvalidNameException();
        }

        if (petModel.getAddress().getStreet().length() > 20
                || petModel.getAddress().getNumber().length() > 20
                || petModel.getAddress().getCity().length() > 20) {
            throw new InvalidAddressException();
        }

        if (petModel.getAge().matches("^\\d+(\\.\\d+)?$")) {
            if (Double.parseDouble(petModel.getAge()) < 0.1
                    || Double.parseDouble(petModel.getAge()) > 20) {
                throw new InvalidAgeException();
            }
        }

        if (petModel.getWeight().matches("^\\d+(\\.\\d+)?$")) {
            if (Double.parseDouble(petModel.getWeight()) < 0.5
                    || Double.parseDouble(petModel.getWeight()) > 60) {
                throw new InvalidWeightException();
            }
        }

        if (petModel.getRace().length() > 15) {
            throw new InvalidRaceException();
        }
    }
}
