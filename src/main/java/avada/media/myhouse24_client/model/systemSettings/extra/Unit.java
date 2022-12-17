package avada.media.myhouse24_client.model.systemSettings.extra;

import avada.media.myhouse24_client.model.common.MappedEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
public class Unit extends MappedEntity {

    private String name;
    private boolean used;

}
