package dev.estv.pet_crud_api.service;

import dev.estv.pet_crud_api.dto.request.LoginRequestDTO;
import dev.estv.pet_crud_api.dto.request.UserRecordDTO;
import dev.estv.pet_crud_api.model.UserModel;
import dev.estv.pet_crud_api.repository.UserRepository;
import dev.estv.pet_crud_api.util.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final Util util;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, Util util) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.util = util;
    }

    @Value("${admin.email}")
    private String adminEmail;

    public UserModel save(UserRecordDTO dto) {

        UserModel newUser = new UserModel();
        newUser.setPassword(passwordEncoder.encode(dto.password()));
        newUser.setEmail(dto.email());
        newUser.setName(dto.name());
        newUser.setNumber(dto.number());

        if (dto.email().equals(adminEmail)) {
            newUser.setRole(UserModel.Role.ROLE_ADMIN);
        } else {
            newUser.setRole(UserModel.Role.ROLE_USER);
        }

        util.validateUser(newUser);
        this.userRepository.save(newUser);

        return newUser;
    }

    @Transactional
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public boolean delete(UUID id) {
        if (!userRepository.existsById(id)) {
            return false;
        }

        userRepository.deleteById(id);
        return true;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public UserModel update(UUID id, UserRecordDTO dto) {
        Optional<UserModel> user = userRepository.findById(id);
        var userModel = user.get();
        BeanUtils.copyProperties(dto, userModel, "password");
        util.validateUser(userModel);
        return userRepository.save(userModel);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserModel findById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public Optional<UserModel> findByEmail(UserRecordDTO dto) {
        return userRepository.findByUsermail(dto.email());
    }

    public Optional<UserModel> login(LoginRequestDTO dto) {
        return userRepository.findByUsermail(dto.email());
    }
}
