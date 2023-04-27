package ee.qrental.balance.repository.impl;

import ee.qrental.balance.adapter.repository.BalanceRepository;
import ee.qrental.balance.repository.spring.BalanceSpringDataRepository;
import ee.qrental.invoice.entity.jakarta.BalanceJakartaEntity;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceRepositoryImpl implements BalanceRepository {

  private final BalanceSpringDataRepository springDataRepository;

  @Override
  public List<BalanceJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public BalanceJakartaEntity save(final BalanceJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public BalanceJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public BalanceJakartaEntity getByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber) {
    return springDataRepository.findByDriverIdAndYearAndWeekNumber(driverId, year, weekNumber);
  }
}
