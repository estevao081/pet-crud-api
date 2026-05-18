package dev.estv.pet_crud_api.controller;

import dev.estv.pet_crud_api.dto.request.PetRecordDTO;
import dev.estv.pet_crud_api.dto.response.ApiResponse;
import dev.estv.pet_crud_api.dto.response.PetResponseDTO;
import dev.estv.pet_crud_api.model.PetModel;
import dev.estv.pet_crud_api.service.PetService;
import dev.estv.pet_crud_api.util.ReturnImageURL;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;
    private final ReturnImageURL returnImageURL;

    public PetController(PetService petService, ReturnImageURL returnImageURL) {
        this.petService = petService;
        this.returnImageURL = returnImageURL;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Void>> save(@RequestBody @Valid PetRecordDTO dto,
                                                  @RequestParam("image") MultipartFile image) {
        String imageUrl = returnImageURL.imageUrl(image);
        petService.save(dto, imageUrl);
        return ResponseEntity.status(201)
                .body(new ApiResponse<>(true, null, "Pet created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PetResponseDTO>>> findAll(@RequestParam int page,
                                                                     @RequestParam int items) {
        Page<PetResponseDTO> pets = petService.listPets(page, items);
        return ResponseEntity.ok(new ApiResponse<>(true, pets, "Pet list"));
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
    public ResponseEntity<ApiResponse<Page<PetResponseDTO>>> search(@RequestBody PetResponseDTO filter,
                                                                    @RequestParam int page,
                                                                    @RequestParam int items) {
        Page<PetResponseDTO> pets = petService.search(filter, page, items);
        return ResponseEntity.ok(new ApiResponse<>(true, pets, "Search result"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PetModel>> update(@PathVariable(value = "id") UUID id,
                                                        @RequestBody @Valid PetRecordDTO dto) {
        if (petService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, null, "Pet not found"));
        }
        PetModel updatedPet = petService.update(id, dto);
        return ResponseEntity.status(200)
                .body(new ApiResponse<>(true, updatedPet, "Pet updated succesfuly"));
    }
}
