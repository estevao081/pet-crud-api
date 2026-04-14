package dev.estv.pet_crud_api.service;

import dev.estv.pet_crud_api.model.UserModel;
import dev.estv.pet_crud_api.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@PreAuthorize("hasRole('ADMIN')")
@Service
public class UserAdminService {

    private final UserRepository userRepository;

    public UserAdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    public UserModel findById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }
}
