package avada.media.myhouse24_client.controller.pages;

import avada.media.myhouse24_client.model.dto.InvoiceDTO;
import avada.media.myhouse24_client.model.response.ResponseByPage;
import avada.media.myhouse24_client.model.dto.StatusDTO;
import avada.media.myhouse24_client.model.request.InvoiceRequest;
import avada.media.myhouse24_client.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class InvoicesPageController {

    private final InvoiceService invoiceService;

    @GetMapping({"/**", ""})
    public ModelAndView showFlatInvoicesPage() {
        return new ModelAndView("pages/invoices");
    }

    @GetMapping("get-all-user-invoices-by-page")
    public @ResponseBody ResponseByPage<InvoiceDTO> getAllInvoicesByPage(InvoiceRequest invoiceRequest) {
        return invoiceService.getAllUserInvoicesByPage(SecurityContextHolder.getContext().getAuthentication().getName(), invoiceRequest);
    }

    @GetMapping("get-invoice-by-unique-number")
    public @ResponseBody InvoiceDTO getInvoiceByUniqueNumber(String uniqueNumber) {
        return invoiceService.getInvoiceByUniqueNumber(SecurityContextHolder.getContext().getAuthentication().getName(), uniqueNumber);
    }

    @GetMapping("get-all-status")
    public @ResponseBody List<StatusDTO> getAllStatus() {
        return invoiceService.getAllStatus();
    }

}
