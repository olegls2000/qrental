package ee.qrental.driver.adapter.repository;

import ee.qrental.driver.entity.jakarta.DriverJakartaEntity;
import java.util.List;

public interface DriverRepository {
  List<DriverJakartaEntity> findAll();

  DriverJakartaEntity save(final DriverJakartaEntity entity);

  DriverJakartaEntity getReferenceById(final Long id);

  DriverJakartaEntity getByIsikukood(final Long isikukood);

  void deleteById(final Long id);
}
