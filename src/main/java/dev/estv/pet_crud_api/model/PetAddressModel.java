package dev.estv.pet_crud_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class PetAddressModel {

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;
}