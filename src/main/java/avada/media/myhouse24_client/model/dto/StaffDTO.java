package avada.media.myhouse24_client.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StaffDTO {

    private Long id;
    private String fullName;
    private RoleDTO role;
    private String title;
    private String email;

    public void setFullName(String lastname, String firstname) {
        StringBuilder fullName = new StringBuilder();
        if (lastname != null && !lastname.equals("")) fullName.append(lastname);
        if (firstname != null && !firstname.equals("")) {
            if (fullName.length() > 0) fullName.append(" ").append(firstname);
            else fullName.append(firstname);
        }
        this.fullName = fullName.toString();
    }

}
