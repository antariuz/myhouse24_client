package avada.media.myhouse24_client.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TariffServiceDTO {

    private Long id;
    private ServiceDTO service;
    private Double price;

}
