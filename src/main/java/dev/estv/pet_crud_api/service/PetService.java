package dev.estv.pet_crud_api.service;

import dev.estv.pet_crud_api.dto.PetRecordDTO;
import dev.estv.pet_crud_api.model.PetModel;
import dev.estv.pet_crud_api.repository.PetRepository;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public void save(@RequestBody PetRecordDTO petRecordDTO) {

        PetModel petModel = PetModel.PetModelBuilder
                .PetModel()
                .name(petRecordDTO.name())
                .type(petRecordDTO.type())
                .gender(petRecordDTO.gender())
                .address(petRecordDTO.address())
                .age(petRecordDTO.age())
                .weight(petRecordDTO.weight())
                .race(petRecordDTO.race())
                .build();

        petRepository.save(petModel);
    }

    public List<PetModel> findAll() {
        return petRepository.findAll();
    }

    public void delete(@PathVariable UUID id) {
        Optional<PetModel> pet = petRepository.findById(id);
        pet.ifPresent(petModel -> petRepository.delete(petModel));
    }
}
