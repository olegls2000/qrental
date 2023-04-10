package ee.qrental.invoice.adapter.repository;

import ee.qrental.invoice.entity.jakarta.InvoiceItemJakartaEntity;

public interface InvoiceItemRepository {
  InvoiceItemJakartaEntity save(final InvoiceItemJakartaEntity entity);

  void removeByInvoiceId(final Long invoiceId);
}
