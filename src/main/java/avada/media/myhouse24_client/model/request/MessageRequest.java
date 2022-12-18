package avada.media.myhouse24_client.model.request;

import lombok.Data;

@Data
public class MessageRequest {

    private String subject;

    private Long userId;

    private Integer pageIndex;
    private Integer pageSize;

}
