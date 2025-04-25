package ee.qrent.billing.invoice.repository.impl;

import ee.qrent.billing.invoice.adapter.repository.InvoiceCalculationResultRepository;
import ee.qrent.billing.invoice.persistence.entity.jakarta.InvoiceCalculationResultJakartaEntity;
import ee.qrent.billing.invoice.repository.spring.InvoiceCalculationResultSpringDataRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceCalculationResultRepositoryImpl implements InvoiceCalculationResultRepository {

  private final InvoiceCalculationResultSpringDataRepository springDataRepository;

  @Override
  public InvoiceCalculationResultJakartaEntity save(
      final InvoiceCalculationResultJakartaEntity entity) {
    return springDataRepository.save(entity);
  }
}
