package ee.qrent.billing.driver.persistence.repository;

import ee.qrent.billing.driver.persistence.entity.jakarta.CallSignJakartaEntity;

import java.time.LocalDate;
import java.util.List;

public interface CallSignRepository {
  List<CallSignJakartaEntity> findAll();
  List<CallSignJakartaEntity> findAvailable(final LocalDate nowDate);

  CallSignJakartaEntity save(final CallSignJakartaEntity entity);

  CallSignJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);

  CallSignJakartaEntity findByCallSign(final Integer callSign);
}
