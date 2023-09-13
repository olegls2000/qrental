package ee.qrental.invoice.repository.spring;

import ee.qrental.invoice.entity.jakarta.InvoiceCalculationResultJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceCalculationResultSpringDataRepository
    extends JpaRepository<InvoiceCalculationResultJakartaEntity, Long> {}
