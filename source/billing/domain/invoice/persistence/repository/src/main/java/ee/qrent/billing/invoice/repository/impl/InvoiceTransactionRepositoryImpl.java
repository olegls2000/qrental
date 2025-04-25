package ee.qrent.billing.invoice.repository.impl;

import ee.qrent.billing.invoice.adapter.repository.InvoiceTransactionRepository;
import ee.qrent.billing.invoice.persistence.entity.jakarta.InvoiceTransactionJakartaEntity;
import ee.qrent.billing.invoice.repository.spring.InvoiceTransactionSpringDataRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceTransactionRepositoryImpl implements InvoiceTransactionRepository {

  private final InvoiceTransactionSpringDataRepository springDataRepository;

  @Override
  public InvoiceTransactionJakartaEntity save(final InvoiceTransactionJakartaEntity entity) {
    return springDataRepository.save(entity);
  }
}
