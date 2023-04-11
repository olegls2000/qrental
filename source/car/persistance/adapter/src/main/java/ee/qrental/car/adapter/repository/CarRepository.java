package ee.qrental.car.adapter.repository;

import ee.qrental.car.entity.jakarta.CarJakartaEntity;
import java.util.List;

public interface CarRepository {
  List<CarJakartaEntity> findAll();

  CarJakartaEntity save(final CarJakartaEntity entity);

  CarJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);
}
