package avada.media.myhouse24_client.service.impl;

import avada.media.myhouse24_client.model.Flat;
import avada.media.myhouse24_client.model.Invoice;
import avada.media.myhouse24_client.model.User;
import avada.media.myhouse24_client.model.dto.FlatDTO;
import avada.media.myhouse24_client.model.dto.ServiceDTO;
import avada.media.myhouse24_client.model.dto.TariffDTO;
import avada.media.myhouse24_client.model.dto.TariffServiceDTO;
import avada.media.myhouse24_client.repo.FlatRepo;
import avada.media.myhouse24_client.repo.InvoiceRepo;
import avada.media.myhouse24_client.repo.UserRepo;
import avada.media.myhouse24_client.service.FlatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlatServiceImpl implements FlatService {

    private final FlatRepo flatRepo;
    private final UserRepo userRepo;
    private final InvoiceRepo invoiceRepo;

    @Override
    public List<FlatDTO> getUserFlats(String userEmail) {
        User user = userRepo.getUserByEmail(userEmail);
        return flatRepo.findAllByUserId(user.getId())
                .stream()
                .map(flat -> {
                    FlatDTO flatDTO = new FlatDTO();
                    flatDTO.setId(flat.getId());
                    flatDTO.setTitle(flat.getNumber(), flat.getBuilding().getTitle());
                    return flatDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TariffDTO getTariffDTOByFlat(Long flatId) {
        Flat flat = flatRepo.findById(flatId).orElseThrow(() -> new EntityNotFoundException("Flat not found with ID: " + flatId));
        TariffDTO tariffDTO = new TariffDTO();
        if (flat.getTariff() != null) {
            tariffDTO.setId(flat.getTariff().getId());
            Hibernate.initialize(flat.getTariff().getTariffServices());
            List<TariffServiceDTO> tariffServiceDTOList = flat.getTariff().getTariffServices().stream()
                    .map(tariffService -> {
                        TariffServiceDTO tariffServiceDTO = new TariffServiceDTO();
                        tariffServiceDTO.setId(tariffService.getId());
                        tariffServiceDTO.setPrice(tariffService.getPrice());
                        ServiceDTO serviceDTO = new ServiceDTO();
                        serviceDTO.setId(tariffService.getService().getId());
                        serviceDTO.setName(tariffService.getService().getName());
                        serviceDTO.setUnit(tariffService.getService().getUnit());
                        tariffServiceDTO.setService(serviceDTO);
                        return tariffServiceDTO;
                    })
                    .collect(Collectors.toList());
            tariffDTO.setTariffServices(tariffServiceDTOList);
        }
        return tariffDTO;
    }

    @Override
    @Transactional
    public Map<String, Object> getUserFlatExpensesInfo(String userEmail, Long flatId) {
        User user = userRepo.getUserByEmail(userEmail);
        Flat userFlat = flatRepo.findByUserIdAndId(user.getId(), flatId);
        Map<String, Object> userFlatExpensesInfo = new HashMap<>();

        //  Balance of the flat
        userFlatExpensesInfo.put("balance", userFlat.getAccount().getBalance());

        //  Account uniques number of the flat
        userFlatExpensesInfo.put("account", userFlat.getAccount().getUniqueNumber());

        Date lastDayOfPastYear = java.sql.Date.valueOf(LocalDate.ofYearDay(LocalDate.now().getYear(), 1).minusDays(1));
        List<Invoice> invoicesOfCurrentYear = invoiceRepo.getInvoicesByUserIdAndFlatIdAndRequestedDateAfter(user.getId(), flatId, lastDayOfPastYear);
        List<List<Invoice>> sortedInvoicesByMonths = sortInvoicesByMonths(invoicesOfCurrentYear);

        //  Monthly Average Expenses Sum
        double averageMonthlyExpense;
        List<Double> monthlySumExpenses = sortedInvoicesByMonths
                .stream()
                .map(monthInvoices -> monthInvoices
                        .stream()
                        .map(invoice -> invoice.getInvoiceService()
                                .stream()
                                .mapToDouble(avada.media.myhouse24_client.model.InvoiceService::getTotalPrice)
                                .sum())
                        .collect(Collectors.toList())
                        .stream()
                        .mapToDouble(Double::doubleValue)
                        .sum())
                .filter(monthInvoicesExpensesSum -> monthInvoicesExpensesSum > 0)
                .collect(Collectors.toList());
        if (!monthlySumExpenses.isEmpty()) {
            averageMonthlyExpense = monthlySumExpenses
                    .stream()
                    .filter(sum -> sum > 0)
                    .collect(Collectors.toList())
                    .stream()
                    .mapToDouble(Double::doubleValue)
                    .sum() / monthlySumExpenses.size();
            userFlatExpensesInfo.put("averageMonthlyExpense", averageMonthlyExpense);
        }

        //  Previously month expenses sum by services
        int prevMonth = LocalDate.now().getMonthValue() - 2;
        List<Invoice> prevMonthInvoices = new ArrayList<>();
        if (prevMonth >= 0) prevMonthInvoices = sortedInvoicesByMonths.get(prevMonth);
        Map<String, Double> prevMonthServicesExpensesSum = getTotalSumByServices(prevMonthInvoices);
        userFlatExpensesInfo.put("prevMonthServicesExpensesSum", prevMonthServicesExpensesSum);

        //  Year Expenses Sum by months
        List<Double> yearExpensesSumByMonths = sortedInvoicesByMonths
                .stream()
                .map(invoices -> invoices
                        .stream()
                        .map(invoice -> invoice.getInvoiceService()
                                .stream()
                                .mapToDouble(avada.media.myhouse24_client.model.InvoiceService::getTotalPrice)
                                .sum())
                        .collect(Collectors.toList())
                        .stream()
                        .mapToDouble(Double::doubleValue)
                        .sum()).collect(Collectors.toList());
        userFlatExpensesInfo.put("yearExpensesSumByMonths", yearExpensesSumByMonths);

        //  Year Expenses Sum by Services
        Map<String, Double> yearExpensesSumByServices = getTotalSumByServices(invoicesOfCurrentYear);
        userFlatExpensesInfo.put("yearExpensesSumByServices", yearExpensesSumByServices);

        return userFlatExpensesInfo;
    }

    private Map<String, Double> getTotalSumByServices(List<Invoice> invoices) {
        Map<String, Double> totalSumByServices = new HashMap<>();
        invoices.forEach(invoice -> {
            List<avada.media.myhouse24_client.model.InvoiceService> invoiceServices = invoice.getInvoiceService();
            invoiceServices.forEach(invoiceService -> {
                avada.media.myhouse24_client.model.systemSettings.pages.Service service = invoiceService.getService();
                Double totalPrice = invoiceService.getTotalPrice();
                if (totalSumByServices.containsKey(service.getName())) {
                    Double prevTotalPriceSum = totalSumByServices.get(service.getName());
                    Double newTotalPriceSum = prevTotalPriceSum + totalPrice;
                    totalSumByServices.put(service.getName(), newTotalPriceSum);
                } else {
                    totalSumByServices.put(service.getName(), totalPrice);
                }
            });
        });
        return totalSumByServices;
    }

    private List<List<Invoice>> sortInvoicesByMonths(List<Invoice> invoices) {
        List<List<Invoice>> monthInvoices = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            int month = i;
            List<Invoice> sortedInvoices = invoices.stream()
                    .filter(invoice -> {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(invoice.getRequestedDate());
                        return calendar.get(Calendar.MONTH) == month;
                    })
                    .collect(Collectors.toList());
            monthInvoices.add(sortedInvoices);
        }
        return monthInvoices;
    }

}
