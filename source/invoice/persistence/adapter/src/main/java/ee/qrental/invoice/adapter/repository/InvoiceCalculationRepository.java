package ee.qrental.invoice.adapter.repository;

import ee.qrental.invoice.entity.jakarta.InvoiceCalculationJakartaEntity;
import java.time.LocalDate;
import java.util.List;

public interface InvoiceCalculationRepository {
  InvoiceCalculationJakartaEntity save(final InvoiceCalculationJakartaEntity entity);

  List<InvoiceCalculationJakartaEntity> findAll();

  InvoiceCalculationJakartaEntity getReferenceById(final Long id);

  InvoiceCalculationJakartaEntity getLastCalculation();
}
