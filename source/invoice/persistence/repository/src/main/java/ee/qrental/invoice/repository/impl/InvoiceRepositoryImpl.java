package ee.qrental.invoice.repository.impl;

import ee.qrental.invoice.adapter.repository.InvoiceRepository;
import ee.qrental.invoice.entity.jakarta.InvoiceJakartaEntity;
import ee.qrental.invoice.repository.spring.InvoiceSpringDataRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceRepositoryImpl implements InvoiceRepository {

  private final InvoiceSpringDataRepository springDataRepository;

  @Override
  public List<InvoiceJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public InvoiceJakartaEntity save(final InvoiceJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public InvoiceJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }

  @Override
  public InvoiceJakartaEntity findByWeekAndDriverIdAndFirmId(
      final Long qWeekId, final Long driverId, final Long firmId) {
    return springDataRepository.findByQWeekIdAndDriverIdAndQFirmId(qWeekId, driverId, firmId);
  }

  @Override
  public InvoiceJakartaEntity findByByQWeekIdAndDriverId(final Long qWeekId, final Long driverId) {
    return springDataRepository.findByByQWeekIdAndDriverId(qWeekId, driverId);
  }

  @Override
  public List<InvoiceJakartaEntity> findByCalculationId(Long calculationId) {
    return springDataRepository.findByCalculationId(calculationId);

  }
}
