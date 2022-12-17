package avada.media.myhouse24_client.model.dto;

import avada.media.myhouse24_client.model.Section;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO {

    private Long id;
    private String uniqueNumber;
    private StatusDTO status;
    private Double balance;
    private BuildingDTO building;
    private Section section;
    private FlatDTO flat;
    private UserDTO user;

}
