package dev.estv.pet_crud_api.controller;

import dev.estv.pet_crud_api.dto.PetRecordDTO;
import dev.estv.pet_crud_api.model.PetModel;
import dev.estv.pet_crud_api.repository.PetRepository;
import dev.estv.pet_crud_api.service.PetService;
import java.util.UUID;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private PetRepository petRepository;

    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody @Valid PetRecordDTO petRecordDTO) {
        petService.save(petRecordDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PetModel>> findAll() {
        if(petService.findAll().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(petService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") UUID id) {
        if(petRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        petService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<PetModel>> search(@RequestBody PetRecordDTO filter) {
        List<PetModel> pets = petService.search(filter);
        if(pets.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pets);
    }

}
