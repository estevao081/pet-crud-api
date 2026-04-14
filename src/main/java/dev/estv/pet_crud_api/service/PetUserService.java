package dev.estv.pet_crud_api.service;

import dev.estv.pet_crud_api.dto.request.PetRecordDTO;
import dev.estv.pet_crud_api.model.PetModel;
import dev.estv.pet_crud_api.model.UserModel;
import dev.estv.pet_crud_api.repository.PetRepository;
import dev.estv.pet_crud_api.repository.UserRepository;
import dev.estv.pet_crud_api.util.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@PreAuthorize("hasRole('USER')")
public class PetUserService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final Util util;

    public PetUserService(PetRepository petRepository, UserRepository userRepository, Util util) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.util = util;
    }

    public PetModel save(PetRecordDTO dto) {
        String usermail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Optional<UserModel> userModelOptional = userRepository.findByUsermail(usermail);
        UserModel user = userModelOptional.get();

        PetModel pet = util.toEntity(dto, user);
        util.validatePet(pet);
        return petRepository.save(pet);
    }

    public boolean delete(UUID id) {
        if (!petRepository.existsById(id)) {
            return false;
        }

        petRepository.deleteById(id);
        return true;
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
