package avada.media.myhouse24_client.model;

import avada.media.myhouse24_client.model.common.MappedEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
public class Section extends MappedEntity {

    private String name;

}
