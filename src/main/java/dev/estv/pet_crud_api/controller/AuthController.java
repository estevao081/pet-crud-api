package dev.estv.pet_crud_api.controller;

import dev.estv.pet_crud_api.dto.request.LoginRequestDTO;
import dev.estv.pet_crud_api.dto.request.UserRecordDTO;
import dev.estv.pet_crud_api.dto.response.ApiResponse;
import dev.estv.pet_crud_api.dto.response.LoginResponseDTO;
import dev.estv.pet_crud_api.model.UserModel;
import dev.estv.pet_crud_api.security.TokenService;
import dev.estv.pet_crud_api.service.NewUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final NewUserService newUserService;

    public AuthController(PasswordEncoder passwordEncoder,
                          TokenService tokenService,
                          NewUserService newUserService) {
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.newUserService = newUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(
            @Valid @RequestBody LoginRequestDTO dto) {

        UserModel user = newUserService.login(dto)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, null, "Invalid credentials"));
        }

        String token = tokenService.generateToken(user);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        new LoginResponseDTO(user.getName(), token),
                        "Login successful"
                )
        );
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> register(
            @Valid @RequestBody UserRecordDTO dto) {

        if (newUserService.findByEmail(dto).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(false, null, "User already exists"));
        }

        UserModel newUser = newUserService.save(dto);
        String token = tokenService.generateToken(newUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        new LoginResponseDTO(dto.name(), token),
                        "User created successfully"
                ));
    }
}
