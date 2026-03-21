package dev.estv.pet_crud_api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import dev.estv.pet_crud_api.exception.InvalidGenderException;
import dev.estv.pet_crud_api.exception.InvalidTypeException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "pets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @ElementCollection
    private List<String> address;
    private Integer age;
    private Integer weight;
    private String race;

    public enum Type {
        CAO, GATO;

        @JsonCreator
        public static Type fromJson(String value) {
            return fromString(value);
        }

        public static Type fromString(String input) {
            if (input == null) {
                throw new IllegalArgumentException("Valor não pode ser nulo");
            }

            return switch (input.trim().toLowerCase()) {
                case "cao" -> CAO;
                case "gato" -> GATO;
                default -> throw new InvalidTypeException();
            };
        }
    }

    public enum Gender {
        F, M;

        @JsonCreator
        public static Gender fromJson(String value) {
            return fromString(value);
        }

        public static Gender fromString(String input) {
            if (input == null) {
                throw new IllegalArgumentException("Valor não pode ser nulo");
            }

            return switch (input.trim().toLowerCase()) {
                case "m" -> M;
                case "f" -> F;
                default -> throw new InvalidGenderException();
            };
        }
    }


    public static final class PetModelBuilder {
        private Long id;
        private String name;
        private Type type;
        private Gender gender;
        private List<String> address;
        private Integer age;
        private Integer weight;
        private String race;

        private PetModelBuilder() {
        }

        public static PetModelBuilder PetModel() {
            return new PetModelBuilder();
        }

        public PetModelBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PetModelBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PetModelBuilder type(Type type) {
            this.type = type;
            return this;
        }

        public PetModelBuilder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public PetModelBuilder address(List<String> address) {
            this.address = address;
            return this;
        }

        public PetModelBuilder age(Integer age) {
            this.age = age;
            return this;
        }

        public PetModelBuilder weight(Integer weight) {
            this.weight = weight;
            return this;
        }

        public PetModelBuilder race(String race) {
            this.race = race;
            return this;
        }

        public PetModel build() {
            PetModel petModel = new PetModel();
            petModel.setId(id);
            petModel.setName(name);
            petModel.setType(type);
            petModel.setGender(gender);
            petModel.setAddress(address);
            petModel.setAge(age);
            petModel.setWeight(weight);
            petModel.setRace(race);
            return petModel;
        }
    }
}
