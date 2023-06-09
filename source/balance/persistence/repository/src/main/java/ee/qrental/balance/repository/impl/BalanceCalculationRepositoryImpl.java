package ee.qrental.balance.repository.impl;

import ee.qrental.balance.adapter.repository.BalanceCalculationRepository;
import ee.qrental.balance.repository.spring.BalanceCalculationSpringDataRepository;
import ee.qrental.invoice.entity.jakarta.BalanceCalculationJakartaEntity;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationRepositoryImpl implements BalanceCalculationRepository {

  private final BalanceCalculationSpringDataRepository springDataRepository;

  @Override
  public BalanceCalculationJakartaEntity save(final BalanceCalculationJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public List<BalanceCalculationJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public BalanceCalculationJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public LocalDate getLastCalculationDate() {
    return springDataRepository.getLastCalculationDate();
  }

  @Override
  public BalanceCalculationJakartaEntity findOneByActionDate(final LocalDate actionDate) {
     return springDataRepository.findByActionDate(actionDate);
  }
}
