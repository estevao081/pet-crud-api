package dev.estv.pet_crud_api.service;

import dev.estv.pet_crud_api.dto.response.PetResponseDTO;
import dev.estv.pet_crud_api.model.PetModel;
import dev.estv.pet_crud_api.repository.PetRepository;
import dev.estv.pet_crud_api.specification.PetSpecification;
import dev.estv.pet_crud_api.util.PetMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<PetResponseDTO> listPets(int page, int items) {
        Pageable pageable = PageRequest.of(page, items);
        return petRepository.findAll(pageable).map(petMapper::toDTO);
    }

    public Page<PetResponseDTO> search(PetResponseDTO filter, int page, int items) {
        Pageable pageable = PageRequest.of(page, items);
        return petRepository.findAll(PetSpecification.filter(filter), pageable).map(petMapper::toDTO);
    }
}
