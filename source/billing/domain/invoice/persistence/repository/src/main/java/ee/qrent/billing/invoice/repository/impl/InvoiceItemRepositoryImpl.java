package ee.qrent.billing.invoice.repository.impl;

import ee.qrent.billing.invoice.adapter.repository.InvoiceItemRepository;
import ee.qrent.billing.invoice.persistence.entity.jakarta.InvoiceItemJakartaEntity;
import ee.qrent.billing.invoice.repository.spring.InvoiceItemSpringDataRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceItemRepositoryImpl implements InvoiceItemRepository {

  private final InvoiceItemSpringDataRepository springDataRepository;

  @Override
  public InvoiceItemJakartaEntity save(final InvoiceItemJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public void removeByInvoiceId(final Long invoiceId) {
    springDataRepository.removeByInvoiceId(invoiceId);
  }
}
