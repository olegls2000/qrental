package ee.qrent.billing.transaction.persistence.repository.impl.balance;

import ee.qrent.billing.transaction.persistence.repository.balance.BalanceRepository;
import ee.qrent.billing.transaction.persistence.entity.jakarta.balance.BalanceJakartaEntity;
import ee.qrent.billing.transaction.persistence.repository.spring.balance.BalanceSpringDataRepository;
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
  public BalanceJakartaEntity getByDriverIdAndQWeekIdAndDerived(
      final Long driverId, final Long qWeekId, final boolean derived) {
    return springDataRepository.findByDriverIdAndQWeekIdAndDerived(driverId, qWeekId, derived);
  }

  @Override
  public BalanceJakartaEntity getLatestByDriverId(final Long driverId) {
    return springDataRepository.findLatestByDriverId(driverId);
  }

  @Override
  public Long getCountByDriverId(final Long driverId) {
    return springDataRepository.getCountByDriverId(driverId);
  }

  @Override
  public BalanceJakartaEntity getLatestByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber) {
    return springDataRepository.findLatestByDriverIdAndYearAndWeekNumber(
        driverId, year, weekNumber);
  }

  @Override
  public BalanceJakartaEntity getLatest() {
    return springDataRepository.findLatest();
  }
}
