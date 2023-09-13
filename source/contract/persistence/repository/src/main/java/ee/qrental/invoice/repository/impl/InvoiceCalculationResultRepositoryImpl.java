package ee.qrental.invoice.repository.impl;

import ee.qrental.invoice.adapter.repository.InvoiceCalculationResultRepository;
import ee.qrental.invoice.entity.jakarta.InvoiceCalculationResultJakartaEntity;
import ee.qrental.invoice.repository.spring.InvoiceCalculationResultSpringDataRepository;
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
