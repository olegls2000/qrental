package ee.qrental.car.repository.impl;

import ee.qrental.car.adapter.repository.CarRepository;
import ee.qrental.car.entity.jakarta.CarJakartaEntity;
import ee.qrental.car.repository.spring.CarSpringDataRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class CarRepositoryImpl implements CarRepository {

  private final CarSpringDataRepository springDataRepository;

  @Override
  public List<CarJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public List<CarJakartaEntity> findNotAvailableByDate(final LocalDate date) {
    return springDataRepository.findNotAvailableByDate(date);
  }

  @Override
  public List<CarJakartaEntity> findByActive(final boolean active) {
    return springDataRepository.findByActive(active);
  }

  @Override
  public CarJakartaEntity save(final CarJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public CarJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }
}
