package avada.media.myhouse24_client.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MasterRequestDTO {

    private Long id;
    private Date requestedDate;
    private String description;
    private String comment;
    private StatusDTO status;
    private RoleDTO role;
    private FlatDTO flat;
    private UserDTO user;
    private String phoneNumber;
    private StaffDTO staff;
    private Date createdAt;

}
