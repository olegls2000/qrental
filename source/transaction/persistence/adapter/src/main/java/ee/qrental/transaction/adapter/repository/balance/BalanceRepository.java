package ee.qrental.transaction.adapter.repository.balance;

import ee.qrental.transaction.entity.jakarta.balance.BalanceJakartaEntity;

import java.util.List;

public interface BalanceRepository {
  List<BalanceJakartaEntity> findAll();

  BalanceJakartaEntity save(final BalanceJakartaEntity entity);

  BalanceJakartaEntity getReferenceById(final Long id);

  // TO\
  BalanceJakartaEntity getByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber);

  BalanceJakartaEntity getByDriverIdAndQWeekIdAndDerived(
      final Long driverId, final Long qWeek, final boolean derived);

  BalanceJakartaEntity getLatestByDriverId(final Long driverId);

  Long getCountByDriverId(final Long driverId);

  BalanceJakartaEntity getLatestByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber);

  BalanceJakartaEntity getLatest();
}
