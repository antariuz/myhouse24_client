package avada.media.myhouse24_client.spec;

import avada.media.myhouse24_client.model.Invoice;
import avada.media.myhouse24_client.model.request.InvoiceRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class InvoiceSpec {

    private final String DEFAULT_SORT_FIELD = "id";

    public Specification<Invoice> getInvoices(InvoiceRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getUserId() != null && request.getUserId() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), request.getUserId()));
            }
            if (request.getRequestedDate() != null && !request.getRequestedDate().equals("")) {
                predicates.add(criteriaBuilder.equal(root.get("requestedDate").as(LocalDate.class), LocalDate.parse(request.getRequestedDate())));
            }
            if (request.getStatus() != null && !request.getStatus().equals("")) {
                predicates.add(criteriaBuilder.equal(root.get("status"), Invoice.Status.valueOf(request.getStatus())));
            }
            if (request.getFlatId() != null && request.getFlatId() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("flat").get("id"), request.getFlatId()));
            }
            if (request.getSortField() == null) query.orderBy(criteriaBuilder.desc(root.get(DEFAULT_SORT_FIELD)));
            else {
                if ("requestedDate".equals(request.getSortField())) {
                    query.orderBy(request.getSortOrder().equals("asc") ? criteriaBuilder.asc(root.get("requestedDate")) : criteriaBuilder.desc(root.get("requestedDate")));
                } else {
                    query.orderBy(criteriaBuilder.desc(root.get(DEFAULT_SORT_FIELD)));
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };

    }
}
