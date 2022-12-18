package avada.media.myhouse24_client.model.dto;

import avada.media.myhouse24_client.model.systemSettings.extra.Unit;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceDTO {

    private Long id;
    private String name;
    private boolean showInCounters;
    private Unit unit;

}
