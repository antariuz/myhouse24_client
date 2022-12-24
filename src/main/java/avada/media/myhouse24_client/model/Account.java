package avada.media.myhouse24_client.model;

import avada.media.myhouse24_client.model.common.MappedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
public class Account extends MappedEntity {

    private String uniqueNumber;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToOne
    @JoinColumn
    private Flat flat;
    private Double balance;

    @RequiredArgsConstructor
    @Getter
    public enum Status {
        ACTIVE("Активный"),
        INACTIVE("Отключен");
        private final String title;
    }

}
