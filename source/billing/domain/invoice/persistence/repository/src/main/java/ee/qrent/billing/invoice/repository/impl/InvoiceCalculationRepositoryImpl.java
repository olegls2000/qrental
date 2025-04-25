package ee.qrent.billing.invoice.repository.impl;

import ee.qrent.billing.invoice.adapter.repository.InvoiceCalculationRepository;
import ee.qrent.billing.invoice.persistence.entity.jakarta.InvoiceCalculationJakartaEntity;
import ee.qrent.billing.invoice.repository.spring.InvoiceCalculationSpringDataRepository;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceCalculationRepositoryImpl implements InvoiceCalculationRepository {

  private final InvoiceCalculationSpringDataRepository springDataRepository;

  @Override
  public InvoiceCalculationJakartaEntity save(final InvoiceCalculationJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public List<InvoiceCalculationJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public InvoiceCalculationJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public InvoiceCalculationJakartaEntity getLastCalculation() {
    return springDataRepository.getLastCalculation();
  }
}
