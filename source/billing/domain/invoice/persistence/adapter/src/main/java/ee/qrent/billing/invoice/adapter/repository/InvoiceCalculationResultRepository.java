package ee.qrent.billing.invoice.adapter.repository;

import ee.qrent.billing.invoice.persistence.entity.jakarta.InvoiceCalculationResultJakartaEntity;

public interface InvoiceCalculationResultRepository {
  InvoiceCalculationResultJakartaEntity save(final InvoiceCalculationResultJakartaEntity entity);
}
