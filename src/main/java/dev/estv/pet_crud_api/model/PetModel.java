package dev.estv.pet_crud_api.model;

import dev.estv.pet_crud_api.exception.InvalidGenderException;
import dev.estv.pet_crud_api.exception.InvalidTypeException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String age;

    @Column(nullable = false)
    private String weight;

    @Column(nullable = false)
    private String race;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserModel owner;

    private LocalDateTime createdAt;

    public enum Type {
        CAO, GATO;

        public static Type fromString(String input) {
            if (input == null || input.isBlank()) {
                return null;
            }

            return switch (input.trim().toUpperCase()) {
                case "CÃO" -> CAO;
                case "GATO" -> GATO;
                default -> throw new InvalidTypeException();
            };
        }
    }

    public enum Gender {
        F, M;

        public static Gender fromString(String input) {
            if (input == null || input.isBlank()) {
                return null;
            }

            return switch (input.trim().toUpperCase()) {
                case "M" -> M;
                case "F" -> F;
                default -> throw new InvalidGenderException();
            };
        }
    }
}
