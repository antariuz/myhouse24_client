package avada.media.myhouse24_client.model.systemSettings.pages;

import avada.media.myhouse24_client.model.common.MappedEntity;
import avada.media.myhouse24_client.model.systemSettings.extra.Unit;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
@Data
public class Service extends MappedEntity {

    private String name;
    private boolean showInCounters;
    private boolean used;
    @OneToOne
    private Unit unit;

}
