package avada.media.myhouse24_client.service.impl;

import avada.media.myhouse24_client.model.Invoice;
import avada.media.myhouse24_client.model.User;
import avada.media.myhouse24_client.model.dto.InvoiceDTO;
import avada.media.myhouse24_client.model.response.ResponseByPage;
import avada.media.myhouse24_client.model.dto.StatusDTO;
import avada.media.myhouse24_client.model.request.InvoiceRequest;
import avada.media.myhouse24_client.repo.InvoiceRepo;
import avada.media.myhouse24_client.repo.UserRepo;
import avada.media.myhouse24_client.service.InvoiceService;
import avada.media.myhouse24_client.spec.InvoiceSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    private final UserRepo userRepo;
    private final InvoiceRepo invoiceRepo;
    private final InvoiceSpec invoiceSpec;

    @Override
    @Transactional
    public ResponseByPage<InvoiceDTO> getAllUserInvoicesByPage(String userEmail, InvoiceRequest invoiceRequest) {
        User user = userRepo.getUserByEmail(userEmail);
        invoiceRequest.setUserId(user.getId());
        Page<Invoice> invoicesPage = invoiceRepo.findAll(invoiceSpec.getInvoices(invoiceRequest), PageRequest.of(invoiceRequest.getPageIndex() - 1, invoiceRequest.getPageSize()));
        ResponseByPage<InvoiceDTO> invoicesDTOByPage = new ResponseByPage<>();
        invoicesDTOByPage.setItemsCount(invoicesPage.getTotalElements());
        invoicesDTOByPage.setPagesCount(invoicesPage.getTotalPages());
        for (Invoice invoice : invoicesPage) {
            invoicesDTOByPage.getData().add(new InvoiceDTO().invoiceToDto(invoice));
        }
        return invoicesDTOByPage;
    }

    @Override
    @Transactional
    public InvoiceDTO getInvoiceByUniqueNumber(String userEmail, String uniqueNumber) {
        User user = userRepo.getUserByEmail(userEmail);
        Optional<Invoice> invoice = invoiceRepo.getInvoiceByUserIdAndUniqueNumber(user.getId(), uniqueNumber);
        return invoice.map(value -> new InvoiceDTO().invoiceToDto(value)).orElse(null);
    }

    @Override
    public List<StatusDTO> getAllStatus() {
        return Arrays.stream(Invoice.Status.values())
                .map(status -> new StatusDTO(status.name(), status.getTitle()))
                .collect(Collectors.toList());
    }

}
