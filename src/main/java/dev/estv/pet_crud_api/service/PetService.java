package dev.estv.pet_crud_api.service;

import dev.estv.pet_crud_api.dto.response.PetResponseDTO;
import dev.estv.pet_crud_api.repository.PetRepository;
import dev.estv.pet_crud_api.specification.PetSpecification;
import dev.estv.pet_crud_api.util.PetMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;

    public PetService(PetRepository petRepository, PetMapper petMapper) {
        this.petRepository = petRepository;
        this.petMapper = petMapper;
    }

    public List<PetResponseDTO> listPets() {
        return petRepository.findAll()
                .stream()
                .map(petMapper::toDTO)
                .toList();
    }

    public List<PetResponseDTO> search(PetResponseDTO filter) {
        return petRepository.findAll(PetSpecification.filter(filter))
                .stream()
                .map(petMapper::toDTO)
                .toList();
    }
}
