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
  public CarLinkJakartaEntity findActiveByDriverIdAndDate(
      final Long driverId, final LocalDate date) {
    return springDataRepository.findActiveByDriverIdAndDate(driverId, date);
  }

  @Override
  public CarLinkJakartaEntity findFirstByDriverId(Long driverId) {
    return springDataRepository.findFirstByDriverIdOrderByDateStartDesc(driverId);
  }

  @Override
  public List<CarLinkJakartaEntity> findActiveByDate(final LocalDate date) {
    return springDataRepository.findActiveByDate(date);
  }

  @Override
  public Long findCountActiveByDate(final LocalDate date) {
    return springDataRepository.findCountActiveByDate(date);
  }

  @Override
  public Long findCountClosedByDate(final LocalDate date) {
    return springDataRepository.findCountClosedByDate(date);
  }

  @Override
  public List<CarLinkJakartaEntity> findClosedByDate(LocalDate date) {
    return springDataRepository.findClosedByDate(date);
  }

  @Override
  public List<CarLinkJakartaEntity> findActiveByCarIdAndDate(
      final Long carId, final LocalDate date) {
    return springDataRepository.findActiveByCarIdAndDate(carId, date);
  }
}
