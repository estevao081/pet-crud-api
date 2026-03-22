package dev.estv.pet_crud_api.service;

import dev.estv.pet_crud_api.dto.request.PetRecordDTO;
import dev.estv.pet_crud_api.exception.InvalidGenderException;
import dev.estv.pet_crud_api.exception.InvalidTypeException;
import dev.estv.pet_crud_api.model.PetModel;
import dev.estv.pet_crud_api.repository.PetRepository;
import dev.estv.pet_crud_api.specification.PetSpecification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public PetModel save(PetRecordDTO petRecordDTO) {
        PetModel petModel = toEntity(petRecordDTO);
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
}
