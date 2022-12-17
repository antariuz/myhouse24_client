package avada.media.myhouse24_client.repo;

import avada.media.myhouse24_client.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice> {

    Optional<Invoice> getInvoiceByUserIdAndUniqueNumber(Long userId, String uniqueNumber);

}
