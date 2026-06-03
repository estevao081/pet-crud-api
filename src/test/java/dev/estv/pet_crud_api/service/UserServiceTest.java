package dev.estv.pet_crud_api.service;

import dev.estv.pet_crud_api.dto.request.LoginRequestDTO;
import dev.estv.pet_crud_api.dto.request.UserRecordDTO;
import dev.estv.pet_crud_api.dto.request.UserUpdateDTO;
import dev.estv.pet_crud_api.dto.response.UserResponseDTO;
import dev.estv.pet_crud_api.model.UserModel;
import dev.estv.pet_crud_api.repository.UserRepository;
import dev.estv.pet_crud_api.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService - Testes Unitários")
class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Util util;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userService, "adminEmail", "admin@pets.com");
    }

    private UserModel buildUserModel(String name, String email) {
        UserModel user = new UserModel();
        user.setId(UUID.randomUUID());
        user.setName(name);
        user.setEmail(email);
        user.setPassword("encoded_password");
        user.setNumber("81900000000");
        user.setRole(UserModel.Role.ROLE_USER);
        return user;
    }

    @Nested
    @DisplayName("save()")
    class Save {

        @Test
        @DisplayName("Deve salvar usuário comum com role ROLE_USER")
        void shouldSaveRegularUserWithRoleUser() {
            UserRecordDTO dto = new UserRecordDTO(
                    "João Silva", "81900000000", "joao@email.com", "senha1234"
            );
            when(passwordEncoder.encode(dto.password())).thenReturn("encoded_password");
            when(userRepository.save(any(UserModel.class))).thenAnswer(inv -> inv.getArgument(0));

            UserModel saved = userService.save(dto);

            assertThat(saved.getRole()).isEqualTo(UserModel.Role.ROLE_USER);
            assertThat(saved.getEmail()).isEqualTo("joao@email.com");
            verify(util).validateUser(any(UserModel.class));
            verify(userRepository).save(any(UserModel.class));
        }

        @Test
        @DisplayName("Deve salvar admin com role ROLE_ADMIN quando email coincide")
        void shouldSaveAdminWithRoleAdmin() {
            UserRecordDTO dto = new UserRecordDTO(
                    "Admin Master", "81900000000", "admin@pets.com", "senha1234"
            );
            when(passwordEncoder.encode(anyString())).thenReturn("encoded");
            when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

            UserModel saved = userService.save(dto);

            assertThat(saved.getRole()).isEqualTo(UserModel.Role.ROLE_ADMIN);
        }

        @Test
        @DisplayName("Deve encodar a senha antes de salvar")
        void shouldEncodePasswordBeforeSaving() {
            UserRecordDTO dto = new UserRecordDTO(
                    "João Silva", "81900000000", "joao@email.com", "senha1234"
            );
            when(passwordEncoder.encode("senha1234")).thenReturn("bcrypt_encoded");
            when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

            UserModel saved = userService.save(dto);

            assertThat(saved.getPassword()).isEqualTo("bcrypt_encoded");
        }
    }

    @Nested
    @DisplayName("delete()")
    class Delete {

        @Test
        @DisplayName("Deve retornar true e deletar quando usuário existe")
        void shouldReturnTrueAndDeleteWhenUserExists() {
            UUID id = UUID.randomUUID();
            when(userRepository.existsById(id)).thenReturn(true);

            boolean result = userService.delete(id);

            assertThat(result).isTrue();
            verify(userRepository).deleteById(id);
        }

        @Test
        @DisplayName("Deve retornar false quando usuário não existe")
        void shouldReturnFalseWhenUserNotFound() {
            UUID id = UUID.randomUUID();
            when(userRepository.existsById(id)).thenReturn(false);

            boolean result = userService.delete(id);

            assertThat(result).isFalse();
            verify(userRepository, never()).deleteById(any());
        }
    }

    @Nested
    @DisplayName("findByEmail() e login()")
    class FindAndLogin {

        @Test
        @DisplayName("findByEmail() deve retornar Optional com user quando existe")
        void shouldReturnUserWhenEmailExists() {
            UserModel user = buildUserModel("João Silva", "joao@email.com");
            UserRecordDTO dto = new UserRecordDTO("João Silva", "81900000000", "joao@email.com", "senha1234");
            when(userRepository.findByUsermail("joao@email.com")).thenReturn(Optional.of(user));

            Optional<UserModel> result = userService.findByEmail(dto);

            assertThat(result).isPresent();
            assertThat(result.get().getEmail()).isEqualTo("joao@email.com");
        }

        @Test
        @DisplayName("findByEmail() deve retornar Optional vazio quando não existe")
        void shouldReturnEmptyWhenEmailNotFound() {
            UserRecordDTO dto = new UserRecordDTO("João Silva", "81900000000", "naoexiste@email.com", "senha1234");
            when(userRepository.findByUsermail("naoexiste@email.com")).thenReturn(Optional.empty());

            Optional<UserModel> result = userService.findByEmail(dto);

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("login() deve retornar usuário pelo email")
        void shouldReturnUserByEmail() {
            UserModel user = buildUserModel("João Silva", "joao@email.com");
            LoginRequestDTO dto = new LoginRequestDTO("joao@email.com", "senha1234");
            when(userRepository.findByUsermail("joao@email.com")).thenReturn(Optional.of(user));

            Optional<UserModel> result = userService.login(dto);

            assertThat(result).isPresent();
        }
    }

    @Nested
    @DisplayName("findAll()")
    class FindAll {

        @Test
        @DisplayName("Deve retornar lista de UserResponseDTO")
        void shouldReturnListOfUserDTOs() {
            UserModel user1 = buildUserModel("João Silva", "joao@email.com");
            UserModel user2 = buildUserModel("Maria Costa", "maria@email.com");

            UserResponseDTO dto1 = new UserResponseDTO(user1.getId().toString(), "João Silva", "81900000000", "joao@email.com", "ROLE_USER");
            UserResponseDTO dto2 = new UserResponseDTO(user2.getId().toString(), "Maria Costa", "81900000001", "maria@email.com", "ROLE_USER");

            when(userRepository.findAll()).thenReturn(List.of(user1, user2));
            when(util.toDTO(user1)).thenReturn(dto1);
            when(util.toDTO(user2)).thenReturn(dto2);

            List<UserResponseDTO> result = userService.findAll();

            assertThat(result).hasSize(2);
            assertThat(result.get(0).email()).isEqualTo("joao@email.com");
        }
    }
}