package avada.media.myhouse24_client.model.systemSettings.pages;

import avada.media.myhouse24_client.model.Status;
import avada.media.myhouse24_client.model.common.MappedEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Staff extends MappedEntity {

    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    @ManyToOne
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

}
