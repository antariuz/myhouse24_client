package avada.media.myhouse24_client.model;

import avada.media.myhouse24_client.model.common.MappedEntity;
import avada.media.myhouse24_client.model.systemSettings.pages.Tariff;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Flat extends MappedEntity {

    private Long number;
    private Double totalSquare;
    @ManyToOne
    private Building building;
    @ManyToOne
    private Section section;
    @ManyToOne
    private Floor floor;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne
    private Tariff tariff;
    @OneToOne(mappedBy = "flat")
    private Account account;

}
