package ee.qrental.invoice.repository.spring;

import ee.qrental.invoice.entity.jakarta.InvoiceItemJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemSpringDataRepository
    extends JpaRepository<InvoiceItemJakartaEntity, Long> {}
