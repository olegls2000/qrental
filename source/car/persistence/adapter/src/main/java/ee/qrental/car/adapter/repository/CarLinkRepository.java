package ee.qrental.car.adapter.repository;

import ee.qrental.car.entity.jakarta.CarLinkJakartaEntity;

import java.time.LocalDate;
import java.util.List;

public interface CarLinkRepository {
  List<CarLinkJakartaEntity> findAll();

  CarLinkJakartaEntity save(final CarLinkJakartaEntity entity);

  CarLinkJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);

  CarLinkJakartaEntity findActiveByDriverIdAndNowDate(final Long driverId, final LocalDate nowDate);

  List<CarLinkJakartaEntity> findActiveByCarIdAndNowDate(final Long carId, final LocalDate nowDate);
}
