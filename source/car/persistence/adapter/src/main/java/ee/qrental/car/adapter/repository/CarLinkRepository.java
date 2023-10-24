package ee.qrental.car.adapter.repository;

import ee.qrental.car.entity.jakarta.CarLinkJakartaEntity;

import java.time.LocalDate;
import java.util.List;

public interface CarLinkRepository {
  List<CarLinkJakartaEntity> findAll();

  CarLinkJakartaEntity save(final CarLinkJakartaEntity entity);

  CarLinkJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);

  CarLinkJakartaEntity findActiveByDriverIdAndDate(final Long driverId, final LocalDate date);

  List<CarLinkJakartaEntity> findActiveByDate(final LocalDate date);

  List<CarLinkJakartaEntity> findActiveByCarIdAndDate(final Long carId, final LocalDate date);
}
