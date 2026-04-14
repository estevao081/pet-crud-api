package dev.estv.pet_crud_api.model;

import dev.estv.pet_crud_api.exception.InvalidGenderException;
import dev.estv.pet_crud_api.exception.InvalidTypeException;
import jakarta.persistence.*;
import lombok.*;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public UserModel getOwner() {
        return owner;
    }

    public void setOwner(UserModel owner) {
        this.owner = owner;
    }

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
