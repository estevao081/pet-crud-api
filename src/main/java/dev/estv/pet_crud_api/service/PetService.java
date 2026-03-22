package dev.estv.pet_crud_api.service;

import dev.estv.pet_crud_api.dto.PetRecordDTO;
import dev.estv.pet_crud_api.model.PetModel;
import dev.estv.pet_crud_api.repository.PetRepository;
import lombok.AllArgsConstructor;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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

    public List<PetModel> search(PetRecordDTO petRecordDTO) {
        return petRepository.findAll(PetSpecification.filter(petRecordDTO));
    }

    public static class PetSpecification {

        public static Specification<PetModel> filter(PetRecordDTO petRecordDTO) {
            return (root, query, cb) -> {

                var predicates = cb.conjunction();

                if (petRecordDTO.name() != null && !petRecordDTO.name().isBlank()) {
                    predicates = cb.and(predicates,
                            cb.like(root.get("name"), "%" + petRecordDTO.name() + "%"));
                }

                if (petRecordDTO.type() != null) {
                    predicates = cb.and(predicates,
                            cb.equal(root.get("type"), petRecordDTO.type()));
                }

                if (petRecordDTO.gender() != null) {
                    predicates = cb.and(predicates,
                            cb.equal(root.get("gender"), petRecordDTO.gender()));
                }

                if (petRecordDTO.age() != null) {
                    predicates = cb.and(predicates,
                            cb.equal(root.get("age"), petRecordDTO.age()));
                }

                if (petRecordDTO.weight() != null) {
                    predicates = cb.and(predicates,
                            cb.equal(root.get("weight"), petRecordDTO.weight()));
                }

                if (petRecordDTO.race() != null && !petRecordDTO.race().isBlank()) {
                    predicates = cb.and(predicates,
                            cb.like(root.get("race"), "%" + petRecordDTO.race() + "%"));
                }

                if (petRecordDTO.type() == null) {
                    throw new IllegalArgumentException("Tipo é obrigatório!");
                }

                return predicates;
            };
        }
    }
}
