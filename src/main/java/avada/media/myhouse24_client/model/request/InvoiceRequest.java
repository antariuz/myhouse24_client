package avada.media.myhouse24_client.model.request;

import lombok.Data;

@Data
public class InvoiceRequest {

    private Long userId;
    private String requestedDate;
    private String status;
    private Long flatId;

    private Integer pageIndex;
    private Integer pageSize;
    private String sortField;
    private String sortOrder;

}
