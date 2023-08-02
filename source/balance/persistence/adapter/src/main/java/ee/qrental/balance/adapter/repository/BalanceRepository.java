package ee.qrental.balance.adapter.repository;

import ee.qrental.invoice.entity.jakarta.BalanceJakartaEntity;
import java.util.List;

public interface BalanceRepository {
  List<BalanceJakartaEntity> findAll();

  BalanceJakartaEntity save(final BalanceJakartaEntity entity);

  BalanceJakartaEntity getReferenceById(final Long id);

  BalanceJakartaEntity getByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber);

  BalanceJakartaEntity getLatestByDriverId(final Long driverId);

  BalanceJakartaEntity getLatestByDriverIdAndYearAndWeekNumber(
          final Long driverId, final Integer year, final Integer weekNumber);
}
