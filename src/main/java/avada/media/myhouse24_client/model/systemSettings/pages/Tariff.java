package avada.media.myhouse24_client.model.systemSettings.pages;

import avada.media.myhouse24_client.model.common.MappedEntity;
import avada.media.myhouse24_client.model.systemSettings.extra.TariffService;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
public class Tariff extends MappedEntity {

    private String name;
    private String description;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<TariffService> tariffServices = new ArrayList<>();
    @UpdateTimestamp
    private Date updatedAt;

}
