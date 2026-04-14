package dev.estv.pet_crud_api.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonPropertyOrder({
        "id",
        "name",
        "type",
        "gender",
        "city",
        "state",
        "age",
        "weight",
        "race",
        "owner"
})
public class PetResponseDTO {

    private String id;
    private String name;
    private String type;
    private String gender;
    private String city;
    private String state;
    private String age;
    private String weight;
    private String race;
    private OwnerDTO owner;

    @Setter
    @Getter
    public static class OwnerDTO {
        private String name;
        private long number;
    }
}
