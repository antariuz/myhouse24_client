package avada.media.myhouse24_client.model;

import avada.media.myhouse24_client.model.common.MappedEntity;
import avada.media.myhouse24_client.model.systemSettings.pages.Role;
import avada.media.myhouse24_client.model.systemSettings.pages.Staff;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
public class MasterRequest extends MappedEntity {

    private Date requestedDate;
    @Column(length = 1048576)
    private String description;
    @Column(length = 1048576)
    private String comment;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    private Role role;
    @ManyToOne
    private User user;
    @ManyToOne
    private Flat flat;
    @ManyToOne
    private Staff staff;
    @CreationTimestamp
    private Date createdAt;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        NEW("Новая"),
        IN_WORK("В работе"),
        DONE("Выполнена");
        private final String title;
    }

}
