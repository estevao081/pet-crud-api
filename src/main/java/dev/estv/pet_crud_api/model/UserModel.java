package dev.estv.pet_crud_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String number;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        ROLE_USER,
        ROLE_ADMIN
    }
}
