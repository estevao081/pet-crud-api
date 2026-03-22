package dev.estv.pet_crud_api.controller;

import dev.estv.pet_crud_api.dto.request.PetRecordDTO;
import dev.estv.pet_crud_api.dto.response.ApiResponse;
import dev.estv.pet_crud_api.model.PetModel;
import dev.estv.pet_crud_api.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> save(@RequestBody @Valid PetRecordDTO dto) {
        petService.save(dto);
        return ResponseEntity.status(201)
                .body(new ApiResponse<>(true, null, "Pet created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PetModel>>> findAll() {

        List<PetModel> pets = petService.findAll();

        return ResponseEntity.ok(
                new ApiResponse<>(true, pets, "Pet list")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {

        boolean deleted = petService.delete(id);

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, null, "Pet not found"));
        }

        return ResponseEntity.ok(
                new ApiResponse<>(true, null, "Pet removed successfully")
        );
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<PetModel>>> search(@RequestBody PetRecordDTO filter) {

        List<PetModel> pets = petService.search(filter);

        return ResponseEntity.ok(
                new ApiResponse<>(true, pets, "Search result")
        );
    }

}
