package ee.qrent.billing.invoice.repository.spring;

import ee.qrent.billing.invoice.persistence.entity.jakarta.InvoiceItemJakartaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemSpringDataRepository
    extends JpaRepository<InvoiceItemJakartaEntity, Long> {

  List<InvoiceItemJakartaEntity> removeByInvoiceId(final Long id);
}
