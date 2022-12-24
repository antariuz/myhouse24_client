package avada.media.myhouse24_client.model.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
public class Image extends MappedEntity {

    private String name;

}
