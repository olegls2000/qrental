package ee.qrent.billing.contract.persistence.repository;

import ee.qrent.billing.contract.persistence.entity.jakarta.AbsenceJakartaEntity;

import java.time.LocalDate;
import java.util.List;

public interface AbsenceRepository {

  AbsenceJakartaEntity findById(Long id);

  List<AbsenceJakartaEntity> findAll();

  AbsenceJakartaEntity save(final AbsenceJakartaEntity entity);

  void deleteById(final Long id);

  List<AbsenceJakartaEntity> findByDriverIdAndDateStartAndDateEnd(
      final Long driverId, final LocalDate dateStart, final LocalDate dateEnd);

  List<AbsenceJakartaEntity> findByDriverIdAndDateStart(
          final Long driverId, final LocalDate dateStart);
}
