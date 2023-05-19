package ee.qrental.driver.adapter.repository;

import ee.qrental.driver.entity.jakarta.CallSignJakartaEntity;
import java.util.List;

public interface CallSignRepository {
  List<CallSignJakartaEntity> findAll();

  CallSignJakartaEntity save(final CallSignJakartaEntity entity);

  CallSignJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);

  CallSignJakartaEntity findByCallSign(final Integer callSign);
}
