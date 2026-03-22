package dev.estv.pet_crud_api.specification;

import dev.estv.pet_crud_api.dto.request.PetRecordDTO;
import dev.estv.pet_crud_api.model.PetModel;
import org.springframework.data.jpa.domain.Specification;

public class PetSpecification {

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

            return predicates;
        };
    }
}
