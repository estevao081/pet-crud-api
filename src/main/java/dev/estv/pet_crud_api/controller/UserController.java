package dev.estv.pet_crud_api.controller;

import dev.estv.pet_crud_api.dto.request.UserRecordDTO;
import dev.estv.pet_crud_api.dto.response.ApiResponse;
import dev.estv.pet_crud_api.model.UserModel;
import dev.estv.pet_crud_api.service.UserAdminService;
import dev.estv.pet_crud_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final UserAdminService userAdminService;

    public UserController(UserService userService, UserAdminService userAdminService) {
        this.userService = userService;
        this.userAdminService = userAdminService;
    }

    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<List<UserModel>>> findAll() {
        List<UserModel> users = userAdminService.findAll();
        return ResponseEntity.ok(new ApiResponse<>(true, users, "Users list"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        boolean deleted = userService.delete(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, null, "User not found"));
        }
        return ResponseEntity.ok(
                new ApiResponse<>(true, null, "User removed successfully")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserModel>> update(@PathVariable(value = "id") UUID id,
                                                         @RequestBody @Valid UserRecordDTO dto) {
        if (userAdminService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, null, "User not found"));
        }
        UserModel updatedUser = userService.update(id, dto);
        return ResponseEntity.status(200)
                .body(new ApiResponse<>(true, updatedUser, "User updated succesfuly"));
    }
}
