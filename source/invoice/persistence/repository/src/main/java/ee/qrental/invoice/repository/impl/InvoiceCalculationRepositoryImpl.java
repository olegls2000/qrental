package ee.qrental.invoice.repository.impl;

import ee.qrental.invoice.adapter.repository.InvoiceCalculationRepository;
import ee.qrental.invoice.entity.jakarta.InvoiceCalculationJakartaEntity;
import ee.qrental.invoice.repository.spring.InvoiceCalculationSpringDataRepository;
import java.time.LocalDate;
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
