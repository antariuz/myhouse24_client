package avada.media.myhouse24_client.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TariffDTO {

    private Long id;
    private String name;
    private String description;
    private String updatedAt;
    private List<TariffServiceDTO> tariffServices;

}
