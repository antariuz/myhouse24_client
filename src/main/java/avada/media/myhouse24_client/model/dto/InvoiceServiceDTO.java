package avada.media.myhouse24_client.model.dto;

import avada.media.myhouse24_client.model.systemSettings.extra.Unit;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceServiceDTO {

    private Long id;
    private ServiceDTO service;
    private Double amount;
    private Unit unit;
    private Double unitPrice;
    private Double totalPrice;

}
