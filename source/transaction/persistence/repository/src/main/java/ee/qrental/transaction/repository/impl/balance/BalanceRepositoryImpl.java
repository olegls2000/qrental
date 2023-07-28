package ee.qrental.transaction.repository.impl.balance;

import ee.qrental.transaction.adapter.repository.balance.BalanceRepository;
import ee.qrental.transaction.repository.spring.balance.BalanceSpringDataRepository;
import ee.qrental.transaction.entity.jakarta.balance.BalanceJakartaEntity;
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

  @Override
  public BalanceJakartaEntity getLatestByDriverId(final Long driverId) {
    return springDataRepository.findLatestByDriverId(driverId);
  }
}
