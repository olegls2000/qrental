package ee.qrental.invoice.repository.spring;

import ee.qrental.invoice.entity.jakarta.InvoiceTransactionJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceTransactionSpringDataRepository
    extends JpaRepository<InvoiceTransactionJakartaEntity, Long> {}
