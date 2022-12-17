package avada.media.myhouse24_client.service;

import avada.media.myhouse24_client.model.dto.InvoiceDTO;
import avada.media.myhouse24_client.model.response.ResponseByPage;
import avada.media.myhouse24_client.model.dto.StatusDTO;
import avada.media.myhouse24_client.model.request.InvoiceRequest;

import java.util.List;

public interface InvoiceService {

    ResponseByPage<InvoiceDTO> getAllUserInvoicesByPage(String userEmail, InvoiceRequest invoiceRequest);

    InvoiceDTO getInvoiceByUniqueNumber(String userEmail, String uniqueNumber);

    List<StatusDTO> getAllStatus();

}
