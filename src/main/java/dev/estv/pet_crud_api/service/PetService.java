package dev.estv.pet_crud_api.service;

import dev.estv.pet_crud_api.dto.request.PetRecordDTO;
import dev.estv.pet_crud_api.dto.request.PetSearchDTO;
import dev.estv.pet_crud_api.model.PetModel;
import dev.estv.pet_crud_api.repository.PetRepository;
import dev.estv.pet_crud_api.specification.PetSpecification;
import dev.estv.pet_crud_api.util.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final Util util;

    public PetService(PetRepository petRepository, Util util) {
        this.petRepository = petRepository;
        this.util = util;
    }

    public PetModel save(PetRecordDTO petRecordDTO) {
        PetModel petModel = util.toEntity(petRecordDTO);
        util.validatePet(petModel);
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

    public PetModel update(UUID id, PetRecordDTO petRecordDTO) {
        Optional<PetModel> pet = petRepository.findById(id);
        var petModel = pet.get();
        BeanUtils.copyProperties(petRecordDTO, petModel);
        util.validatePet(petModel);
        return petRepository.save(petModel);
    }

    public PetModel findById(UUID id) {
        return petRepository.findById(id).orElse(null);
    }
}
