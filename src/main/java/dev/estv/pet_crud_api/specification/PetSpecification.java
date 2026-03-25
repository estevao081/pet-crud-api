package dev.estv.pet_crud_api.specification;

import dev.estv.pet_crud_api.dto.request.PetSearchDTO;
import dev.estv.pet_crud_api.exception.InvalidAddressException;
import dev.estv.pet_crud_api.exception.InvalidTypeException;
import dev.estv.pet_crud_api.model.PetModel;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class PetSpecification {

    public static Specification<PetModel> filter(PetSearchDTO dto) {

        return (root, query, cb) -> {

            if (dto.dtoAddress().state() == null || dto.dtoAddress().city() == null) {
                throw new InvalidAddressException();
            }

            var predicates = new ArrayList<Predicate>();

            if (hasValue(dto.name())) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("name")),
                                "%" + dto.name().toLowerCase() + "%"
                        )
                );
            }

            if (hasValue(dto.type())) {
                predicates.add(
                        cb.equal(
                                root.get("type"),
                                PetModel.Type.fromString(dto.type())
                        )
                );
            }

            if (hasValue(dto.gender())) {
                predicates.add(
                        cb.equal(
                                root.get("gender"),
                                PetModel.Gender.fromString(dto.gender())
                        )
                );
            }

            if (hasValue(dto.age())) {
                predicates.add(
                        cb.equal(root.get("age"),
                                Integer.parseInt(dto.age()))
                );
            }

            if (hasValue(dto.weight())) {
                predicates.add(
                        cb.equal(root.get("weight"),
                                Double.parseDouble(dto.weight()))
                );
            }

            if (hasValue(dto.dtoAddress().city())) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("city")),
                                "%" + dto.dtoAddress().city().toLowerCase() + "%"
                        )
                );
            }

            if (hasValue(dto.dtoAddress().state())) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("state")),
                                "%" + dto.dtoAddress().state().toLowerCase() + "%"
                        )
                );
            }

            if (hasValue(dto.race())) {
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

    private static boolean hasValue(String value) {
        return value != null && !value.isBlank();
    }
}
