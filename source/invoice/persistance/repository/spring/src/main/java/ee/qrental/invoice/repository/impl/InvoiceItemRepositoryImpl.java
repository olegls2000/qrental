package ee.qrental.invoice.repository.impl;

import ee.qrental.invoice.adapter.repository.InvoiceItemRepository;
import ee.qrental.invoice.entity.jakarta.InvoiceItemJakartaEntity;
import ee.qrental.invoice.repository.spring.InvoiceItemSpringDataRepository;
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
