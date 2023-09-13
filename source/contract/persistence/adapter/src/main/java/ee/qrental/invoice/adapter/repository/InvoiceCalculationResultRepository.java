package ee.qrental.invoice.adapter.repository;

import ee.qrental.invoice.entity.jakarta.InvoiceCalculationResultJakartaEntity;

public interface InvoiceCalculationResultRepository {
  InvoiceCalculationResultJakartaEntity save(final InvoiceCalculationResultJakartaEntity entity);
}
