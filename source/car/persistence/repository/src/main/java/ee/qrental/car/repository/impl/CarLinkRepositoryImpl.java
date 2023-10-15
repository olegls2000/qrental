package ee.qrental.car.repository.impl;

import ee.qrental.car.adapter.repository.CarLinkRepository;
import ee.qrental.car.entity.jakarta.CarLinkJakartaEntity;
import ee.qrental.car.repository.spring.CarLinkSpringDataRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class CarLinkRepositoryImpl implements CarLinkRepository {

  private final CarLinkSpringDataRepository springDataRepository;

  @Override
  public List<CarLinkJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public CarLinkJakartaEntity save(final CarLinkJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public CarLinkJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }

  @Override
  public CarLinkJakartaEntity findActiveByDriverIdAndNowDate(
      final Long driverId, final LocalDate nowDate) {
    return springDataRepository.findActiveByDriverIdAndNowDate(driverId, nowDate);
  }

  @Override
  public List<CarLinkJakartaEntity> findActiveByCarIdAndNowDate(
      final Long carId, final LocalDate nowDate) {
    return springDataRepository.findActiveByCarIdAndNowDate(carId, nowDate);
  }
}
