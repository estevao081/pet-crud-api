package dev.estv.pet_crud_api.service;

import dev.estv.pet_crud_api.dto.request.UserRecordDTO;
import dev.estv.pet_crud_api.model.UserModel;
import dev.estv.pet_crud_api.repository.UserRepository;
import dev.estv.pet_crud_api.util.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@PreAuthorize("hasRole('USER')")
public class UserService {

    private final UserRepository userRepository;
    private final Util util;


    public UserService(UserRepository userRepository, Util util) {
        this.userRepository = userRepository;
        this.util = util;
    }

    public boolean delete(UUID id) {
        if (!userRepository.existsById(id)) {
            return false;
        }

        userRepository.deleteById(id);
        return true;
    }

    public UserModel update(UUID id, UserRecordDTO dto) {
        Optional<UserModel> user = userRepository.findById(id);
        var userModel = user.get();
        BeanUtils.copyProperties(dto, userModel);
        util.validateUser(userModel);
        return userRepository.save(userModel);
    }
}
