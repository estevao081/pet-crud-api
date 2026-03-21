package dev.estv.pet_crud_api.repository;

import dev.estv.pet_crud_api.model.PetModel;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<PetModel, UUID> {
}
