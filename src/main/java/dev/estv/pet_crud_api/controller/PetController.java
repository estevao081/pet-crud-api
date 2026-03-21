package dev.estv.pet_crud_api.controller;

import dev.estv.pet_crud_api.dto.PetRecordDTO;
import dev.estv.pet_crud_api.model.PetModel;
import dev.estv.pet_crud_api.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody PetRecordDTO petRecordDTO) {
        petService.save(petRecordDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PetModel>> findAll() {
        return ResponseEntity.ok(petService.findAll());
    }
}
