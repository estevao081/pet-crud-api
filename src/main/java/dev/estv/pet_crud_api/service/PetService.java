package dev.estv.pet_crud_api.service;

import dev.estv.pet_crud_api.dto.request.PetRecordDTO;
import dev.estv.pet_crud_api.dto.response.PetResponseDTO;
import dev.estv.pet_crud_api.model.PetModel;
import dev.estv.pet_crud_api.model.UserModel;
import dev.estv.pet_crud_api.repository.PetRepository;
import dev.estv.pet_crud_api.repository.UserRepository;
import dev.estv.pet_crud_api.specification.PetSpecification;
import dev.estv.pet_crud_api.util.PetMapper;
import dev.estv.pet_crud_api.util.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class PetService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final Util util;

    public PetService(UserRepository userRepository, PetRepository petRepository, PetMapper petMapper, Util util) {
        this.userRepository = userRepository;
        this.petRepository = petRepository;
        this.petMapper = petMapper;
        this.util = util;
    }

    public Page<PetResponseDTO> listPets(int page, int items) {
        Pageable pageable = PageRequest.of(page, items, Sort.by("createdAt").descending());
        return petRepository.findAll(pageable).map(petMapper::toDTO);
    }

    public Page<PetResponseDTO> search(PetResponseDTO filter, int page, int items) {
        Pageable pageable = PageRequest.of(page, items, Sort.by("createdAt").descending());
        return petRepository.findAll(PetSpecification.filter(filter), pageable).map(petMapper::toDTO);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public PetModel save(PetRecordDTO dto, String imageUrl) {
        String usermail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Optional<UserModel> userModelOptional = userRepository.findByUsermail(usermail);
        UserModel user = userModelOptional.get();

        PetModel pet = util.toEntity(dto, user);
        pet.setImageUrl(imageUrl);
        util.validatePet(pet);

        user.setPetsId(Collections.singletonList(pet.getId()));

        return petRepository.save(pet);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public boolean delete(UUID id) {
        if (!petRepository.existsById(id)) {
            return false;
        }

        petRepository.deleteById(id);
        return true;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public PetModel update(UUID id, PetRecordDTO dto, String newImageUrl) {
        var petModel = petRepository.findById(id).get();
        String oldImage = petModel.getImageUrl();
        BeanUtils.copyProperties(dto, petModel);
        petModel.setImageUrl(newImageUrl != null ? newImageUrl : oldImage);
        util.validatePet(petModel);
        return petRepository.save(petModel);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public PetModel findById(UUID id) {
        return petRepository.findById(id).orElse(null);
    }
}
