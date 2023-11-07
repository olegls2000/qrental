package ee.qrental.car.adapter.repository;

import ee.qrental.car.entity.jakarta.CarJakartaEntity;

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
