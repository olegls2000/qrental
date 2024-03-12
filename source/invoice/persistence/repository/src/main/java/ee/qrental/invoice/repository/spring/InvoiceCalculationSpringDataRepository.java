package ee.qrental.invoice.repository.spring;

import ee.qrental.invoice.entity.jakarta.InvoiceCalculationJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceCalculationSpringDataRepository
    extends JpaRepository<InvoiceCalculationJakartaEntity, Long> {

  @Query(
      value = "select * from invoice_calculation order by end_q_week_id desc limit 1",
      nativeQuery = true)
  InvoiceCalculationJakartaEntity getLastCalculation();
}
