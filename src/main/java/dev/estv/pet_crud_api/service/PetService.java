package dev.estv.pet_crud_api.service;

import dev.estv.pet_crud_api.dto.request.PetRecordDTO;
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

    public List<PetModel> search(PetRecordDTO petRecordDTO) {
        if (petRecordDTO.type() == null) {
            throw new IllegalArgumentException("Type is required");
        }
        return petRepository.findAll(PetSpecification.filter(petRecordDTO));
    }

    private PetModel toEntity(PetRecordDTO dto) {
        return PetModel.PetModelBuilder
                .PetModel()
                .name(dto.name())
                .type(dto.type())
                .gender(dto.gender())
                .address(dto.address())
                .age(dto.age())
                .weight(dto.weight())
                .race(dto.race())
                .build();
    }
}
