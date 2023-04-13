package ee.qrental.invoice.repository.spring;

import ee.qrental.invoice.entity.jakarta.InvoiceItemJakartaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemSpringDataRepository
    extends JpaRepository<InvoiceItemJakartaEntity, Long> {

  List<InvoiceItemJakartaEntity> removeByInvoiceId(final Long id);
}
