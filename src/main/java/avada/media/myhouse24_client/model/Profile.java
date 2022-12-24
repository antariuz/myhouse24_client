package avada.media.myhouse24_client.model;

import avada.media.myhouse24_client.model.common.MappedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
public class Profile extends MappedEntity {

    private String firstname;
    private String middleName;
    private String lastname;
    private LocalDate birthdate;
    private String notes;
    private String phoneNumber;
    private String viberLogin;
    private String telegramLogin;
    private String profileImage;

}
