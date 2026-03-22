package dev.estv.pet_crud_api.specification;

import dev.estv.pet_crud_api.dto.request.PetRecordDTO;
import dev.estv.pet_crud_api.dto.request.PetSearchDTO;
import dev.estv.pet_crud_api.model.PetModel;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class PetSpecification {

    public static Specification<PetModel> filter(PetSearchDTO dto) {

        return (root, query, cb) -> {

            query.distinct(true);

            var predicates = new ArrayList<Predicate>();

            predicates.add(cb.equal(root.get("type"), dto.type()));

            if (dto.name() != null && !dto.name().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("name")),
                                "%" + dto.name().toLowerCase() + "%"
                        )
                );
            }

            if (dto.gender() != null && !dto.gender().isBlank()) {
                predicates.add(
                        cb.equal(
                                root.get("gender"),
                                PetModel.Gender.fromString(dto.gender())
                        )
                );
            }

            if (dto.age() != null) {
                predicates.add(cb.equal(root.get("age"), dto.age()));
            }

            if (dto.weight() != null) {
                predicates.add(cb.equal(root.get("weight"), dto.weight()));
            }

            if (dto.race() != null && !dto.race().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("race")),
                                "%" + dto.race().toLowerCase() + "%"
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
