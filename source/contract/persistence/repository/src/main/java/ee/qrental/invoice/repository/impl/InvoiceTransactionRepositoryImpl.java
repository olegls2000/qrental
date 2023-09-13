package ee.qrental.invoice.repository.impl;

import ee.qrental.invoice.adapter.repository.InvoiceTransactionRepository;
import ee.qrental.invoice.entity.jakarta.InvoiceTransactionJakartaEntity;
import ee.qrental.invoice.repository.spring.InvoiceTransactionSpringDataRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceTransactionRepositoryImpl implements InvoiceTransactionRepository {

  private final InvoiceTransactionSpringDataRepository springDataRepository;

  @Override
  public InvoiceTransactionJakartaEntity save(final InvoiceTransactionJakartaEntity entity) {
    return springDataRepository.save(entity);
  }
}
