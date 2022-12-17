package avada.media.myhouse24_client.model.dto;

import avada.media.myhouse24_client.model.Invoice;
import avada.media.myhouse24_client.model.Section;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceDTO {

    private Long id;
    private String uniqueNumber;
    private Date requestedDate;
    private BuildingDTO building;
    private Section section;
    private UserDTO user;
    private FlatDTO flat;
    private AccountDTO account;
    private StatusDTO status;
    private TariffDTO tariff;
    private Date periodStart;
    private Date periodEnd;
    private boolean used;
    private List<InvoiceServiceDTO> invoiceServices = new ArrayList<>();
    private Double totalAmount;


    public InvoiceDTO invoiceToDto(Invoice invoice) {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setId(invoice.getId());
        invoiceDTO.setUniqueNumber(invoice.getUniqueNumber());
        invoiceDTO.setRequestedDate(invoice.getRequestedDate());
        invoiceDTO.setStatus(new StatusDTO(invoice.getStatus().name(), invoice.getStatus().getTitle()));
        invoiceDTO.setInvoiceServices(invoice.getInvoiceService()
                .stream()
                .map(invoiceService -> {
                    InvoiceServiceDTO invoiceServiceDTO = new InvoiceServiceDTO();
                    invoiceServiceDTO.setId(invoiceService.getId());
                    ServiceDTO serviceDTO = new ServiceDTO();
                    serviceDTO.setId(invoiceService.getService().getId());
                    serviceDTO.setName(invoiceService.getService().getName());
                    serviceDTO.setUnit(invoiceService.getService().getUnit());
                    invoiceServiceDTO.setService(serviceDTO);
                    invoiceServiceDTO.setAmount(invoiceService.getAmount());
                    invoiceServiceDTO.setUnit(invoiceService.getUnit());
                    invoiceServiceDTO.setUnitPrice(invoiceService.getUnitPrice());
                    invoiceServiceDTO.setTotalPrice(invoiceService.getTotalPrice());
                    return invoiceServiceDTO;
                })
                .collect(Collectors.toList()));
        invoiceDTO.setTotalAmount(invoice.getInvoiceService().stream().mapToDouble(avada.media.myhouse24_client.model.InvoiceService::getTotalPrice).sum());
        return invoiceDTO;
    }

}
