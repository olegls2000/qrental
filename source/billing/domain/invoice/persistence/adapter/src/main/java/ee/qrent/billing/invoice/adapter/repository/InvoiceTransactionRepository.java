package ee.qrent.billing.invoice.adapter.repository;

import ee.qrent.billing.invoice.persistence.entity.jakarta.InvoiceTransactionJakartaEntity;

public interface InvoiceTransactionRepository {
  InvoiceTransactionJakartaEntity save(final InvoiceTransactionJakartaEntity entity);
}
