package avada.media.myhouse24_client.model.systemSettings.extra;

import avada.media.myhouse24_client.model.common.MappedEntity;
import avada.media.myhouse24_client.model.systemSettings.pages.Service;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
@Data
public class TariffService extends MappedEntity {

    @ManyToOne
    private Service service;
    private Double price;

}
