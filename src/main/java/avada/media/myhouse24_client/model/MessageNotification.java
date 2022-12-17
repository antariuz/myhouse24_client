package avada.media.myhouse24_client.model;

import avada.media.myhouse24_client.model.common.MappedEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class MessageNotification extends MappedEntity {

    private boolean wasViewed;

    @ManyToOne(fetch = FetchType.LAZY)
    private Message message;

    @OneToOne
    private User user;

}
