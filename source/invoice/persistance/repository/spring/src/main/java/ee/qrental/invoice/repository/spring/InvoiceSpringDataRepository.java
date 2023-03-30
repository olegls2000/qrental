package ee.qrental.invoice.repository.spring;

import ee.qrental.invoice.entity.jakarta.InvoiceJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceSpringDataRepository extends JpaRepository<InvoiceJakartaEntity, Long> {}
