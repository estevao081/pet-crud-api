package dev.estv.pet_crud_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetModel {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @ElementCollection
    @CollectionTable(name = "pet_addresses", joinColumns = @JoinColumn(name = "pet_id"))
    @Column(name = "address")
    private List<String> address;

    private Integer age;
    private Integer weight;
    private String race;

    public enum Type {
        CAO, GATO
    }

    public enum Gender {
        F, M
    }
}
