package ee.qrental.invoice.adapter.repository;

import ee.qrental.invoice.entity.jakarta.InvoiceTransactionJakartaEntity;

public interface InvoiceTransactionRepository {
  InvoiceTransactionJakartaEntity save(final InvoiceTransactionJakartaEntity entity);
}
