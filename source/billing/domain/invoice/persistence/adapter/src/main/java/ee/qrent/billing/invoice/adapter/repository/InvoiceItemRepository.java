package ee.qrent.billing.invoice.adapter.repository;

import ee.qrent.billing.invoice.persistence.entity.jakarta.InvoiceItemJakartaEntity;

public interface InvoiceItemRepository {
  InvoiceItemJakartaEntity save(final InvoiceItemJakartaEntity entity);

  void removeByInvoiceId(final Long invoiceId);
}
