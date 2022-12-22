package avada.media.myhouse24_client.model;

import avada.media.myhouse24_client.model.common.MappedEntity;
import avada.media.myhouse24_client.model.systemSettings.pages.Staff;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
public class Message extends MappedEntity {

    private String subject;
    @Column(length = 104857)
    private String text;
    @ManyToMany
    @JoinTable(
            name = "message_user",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();
    @ManyToOne
    private Staff staff;
    private String toWhom;
    @OneToMany(
            mappedBy = "message",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MessageNotification> messageNotifications = new ArrayList<>();
    @CreationTimestamp
    private Date createdAt;

}
