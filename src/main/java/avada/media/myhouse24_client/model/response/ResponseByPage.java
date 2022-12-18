package avada.media.myhouse24_client.model.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseByPage<X> {

    private List<X> data = new ArrayList<>();
    private Long itemsCount;
    private Integer pagesCount;

}
