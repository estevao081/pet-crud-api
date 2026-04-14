package dev.estv.pet_crud_api.service;

import dev.estv.pet_crud_api.dto.request.LoginRequestDTO;
import dev.estv.pet_crud_api.dto.request.UserRecordDTO;
import dev.estv.pet_crud_api.model.UserModel;
import dev.estv.pet_crud_api.repository.UserRepository;
import dev.estv.pet_crud_api.util.Util;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NewUserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final Util util;

    public NewUserService(PasswordEncoder passwordEncoder, UserRepository userRepository, Util util) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.util = util;
    }

    public UserModel save(UserRecordDTO dto) {
        UserModel newUser = new UserModel();
        newUser.setPassword(passwordEncoder.encode(dto.password()));
        newUser.setEmail(dto.email());
        newUser.setName(dto.name());
        newUser.setNumber(dto.number());

        if (dto.email().equals("thiagoestv18@gmail.com")) {
            newUser.setRole(UserModel.Role.ROLE_ADMIN);
        } else {
            newUser.setRole(UserModel.Role.ROLE_USER);
        }

        util.validateUser(newUser);
        this.userRepository.save(newUser);

        return newUser;
    }

    public Optional<UserModel> findByEmail(UserRecordDTO dto) {
        return userRepository.findByUsermail(dto.email());
    }

    public Optional<UserModel> login(LoginRequestDTO dto) {
        return userRepository.findByUsermail(dto.email());
    }
}
