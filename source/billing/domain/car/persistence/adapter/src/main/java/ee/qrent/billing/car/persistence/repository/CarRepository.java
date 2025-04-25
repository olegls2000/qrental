package ee.qrent.billing.car.persistence.repository;

import ee.qrent.billing.car.persistence.entity.jakarta.CarJakartaEntity;

import java.time.LocalDate;
import java.util.List;

public interface CarRepository {
  List<CarJakartaEntity> findAll();

  List<CarJakartaEntity> findNotAvailableByDate(final LocalDate date);

  List<CarJakartaEntity> findByActive(final boolean active);

  CarJakartaEntity save(final CarJakartaEntity entity);

  CarJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);
}
