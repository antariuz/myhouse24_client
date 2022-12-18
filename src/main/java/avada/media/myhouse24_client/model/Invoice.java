package avada.media.myhouse24_client.model;

import avada.media.myhouse24_client.model.common.MappedEntity;
import avada.media.myhouse24_client.model.systemSettings.pages.Tariff;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class Invoice extends MappedEntity {

    private String uniqueNumber;
    private Date requestedDate;
    @ManyToOne
    private User user;
    @ManyToOne
    private Flat flat;
    @ManyToOne
    private Account account;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    private Tariff tariff;
    private Date periodStart;
    private Date periodEnd;
    @OneToMany
    private List<InvoiceService> invoiceService = new ArrayList<>();
    private boolean used;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        PAID("Оплачена"),
        PARTLY_PAID("Частично оплачена"),
        UNPAID("Неоплачена");

        private final String title;
    }

}
