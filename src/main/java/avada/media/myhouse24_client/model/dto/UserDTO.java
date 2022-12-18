package avada.media.myhouse24_client.model.dto;

import avada.media.myhouse24_client.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Long id;

    private String uniqueId;
    private String fullName;
    private String email;
    private Set<BuildingDTO> buildings;
    private List<FlatDTO> flats;
    private String createdAt;
    private StatusDTO status;
    private Boolean hasDebt;

    private String firstname;
    private String middleName;
    private String lastname;
    private LocalDate birthdate;
    private String notes;
    private String phoneNumber;
    private String viberLogin;
    private String telegramLogin;
    private String profileImage;

    public void setFullName(String lastname, String firstname, String middleName) {
        StringBuilder fullName = new StringBuilder();
        if (lastname != null && !lastname.equals("")) fullName.append(lastname);
        if (firstname != null && !firstname.equals("")) {
            if (fullName.length() > 0) fullName.append(" ").append(firstname);
            else fullName.append(firstname);
        }
        if (middleName != null && !middleName.equals("")) {
            if (fullName.length() > 0) fullName.append(" ").append(middleName);
            else fullName.append(middleName);
        }
        this.fullName = fullName.toString();
    }

    //  TODO: rework
    public UserDTO userToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        return userDTO;
    }

}
