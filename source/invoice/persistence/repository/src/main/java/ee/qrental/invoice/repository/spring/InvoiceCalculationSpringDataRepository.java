package ee.qrental.invoice.repository.spring;

import ee.qrental.invoice.entity.jakarta.InvoiceCalculationJakartaEntity;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceCalculationSpringDataRepository
    extends JpaRepository<InvoiceCalculationJakartaEntity, Long> {

  @Query(
      value = "select end_date from invoice_calculation order by end_date desc limit 1",
      nativeQuery = true)
  LocalDate getLastCalculationDate();
}
